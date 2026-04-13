package com.example.Auth;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.Auth.auth.dto.LoginRequest;
import com.example.Auth.auth.dto.RegisterRequest;
import com.example.Auth.checkout.model.Checkout;
import com.example.Auth.checkout.repository.CheckoutRepository;
import com.example.Auth.incommingtranx.model.IncommingTranx;
import com.example.Auth.incommingtranx.repository.IncommingTranxRepository;
import com.example.Auth.khqrlinkacc.model.KhqrLinkAcc;
import com.example.Auth.khqrlinkacc.repository.KhqrLinkAccRepository;
import com.example.Auth.merchantinfo.model.MerchantInfo;
import com.example.Auth.merchantinfo.repository.MerchantInfoRepository;
import com.example.Auth.merchant.model.Merchant;
import com.example.Auth.merchant.repository.MerchantRepository;
import com.example.Auth.parentmerchant.model.ParentMerchant;
import com.example.Auth.parentmerchant.repository.ParentMerchantRepository;
import com.example.Auth.user.model.AppUser;
import com.example.Auth.user.repository.UserRepository;
import com.example.Auth.tbluser.model.TblUser;
import com.example.Auth.tbluser.repository.TblUserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TblUserRepository tblUserRepository;

	@Autowired
	private ParentMerchantRepository parentMerchantRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MerchantRepository merchantRepository;

	@Autowired
	private MerchantInfoRepository merchantInfoRepository;

	@Autowired
	private KhqrLinkAccRepository khqrLinkAccRepository;

	@Autowired
	private CheckoutRepository checkoutRepository;

	@Autowired
	private IncommingTranxRepository incommingTranxRepository;

	@Test
	void registerLoginLogoutFlow() throws Exception {
		String email = "roeun." + UUID.randomUUID() + "@example.com";
		String expectedUsername = email.substring(0, email.indexOf("@"));

		RegisterRequest registerRequest = new RegisterRequest("Roeun Dary", email, "Password123");
		MvcResult registerResult = mockMvc.perform(
						post("/api/auth/register")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(registerRequest))
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.token").isString())
				.andExpect(jsonPath("$.tokenType").value("Bearer"))
				.andExpect(jsonPath("$.user.email").value(email.toLowerCase()))
				.andExpect(jsonPath("$.user.username").value(expectedUsername))
				.andExpect(jsonPath("$.user.enabled").value(true))
				.andReturn();

		AppUser registeredUser = userRepository.findByEmail(email.toLowerCase()).orElseThrow();
		org.junit.jupiter.api.Assertions.assertEquals(expectedUsername, registeredUser.getAppUsername());
		org.junit.jupiter.api.Assertions.assertTrue(registeredUser.getEnabled());

		String registerToken = readToken(registerResult);

		mockMvc.perform(
						get("/api/auth/me")
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + registerToken)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value(email.toLowerCase()));

		LoginRequest loginRequest = new LoginRequest(email, "Password123");
		MvcResult loginResult = mockMvc.perform(
						post("/api/auth/login")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(loginRequest))
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token").isString())
				.andExpect(jsonPath("$.tokenType").value("Bearer"))
				.andReturn();

		org.junit.jupiter.api.Assertions.assertEquals(1L, userRepository.findAll().stream().filter(user -> user.getEmail().equals(email.toLowerCase())).count());

		String loginToken = readToken(loginResult);

		mockMvc.perform(
						post("/api/auth/logout")
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + loginToken)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Logged out successfully"));

		mockMvc.perform(
						get("/api/auth/me")
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + loginToken)
				)
				.andExpect(status().isUnauthorized());
	}

	@Test
	void registerRejectsDuplicateEmail() throws Exception {
		String email = "duplicate." + UUID.randomUUID() + "@example.com";
		RegisterRequest request = new RegisterRequest("Duplicate User", email, "Password123");

		mockMvc.perform(
						post("/api/auth/register")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(request))
				)
				.andExpect(status().isCreated());

		mockMvc.perform(
						post("/api/auth/register")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(request))
				)
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.message").value("Email is already registered"));
	}

	@Test
	void loginRejectsWrongPassword() throws Exception {
		String email = "login." + UUID.randomUUID() + "@example.com";
		RegisterRequest registerRequest = new RegisterRequest("Login User", email, "Password123");

		mockMvc.perform(
						post("/api/auth/register")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(registerRequest))
				)
				.andExpect(status().isCreated());

		LoginRequest loginRequest = new LoginRequest(email, "WrongPassword");

		mockMvc.perform(
						post("/api/auth/login")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(loginRequest))
				)
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.message").value("Email or password is incorrect"));
	}

	@Test
	void roleCrudFlow() throws Exception {
		String email = "role." + UUID.randomUUID() + "@example.com";
		String token = registerAndGetToken(email);

		MvcResult createResult = mockMvc.perform(
						post("/api/roles")
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(Map.of(
										"rolesId", 101,
										"rolesType", "superadmin",
										"status", "enable"
								)))
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.rolesId").value(101))
				.andExpect(jsonPath("$.rolesType").value("superadmin"))
				.andExpect(jsonPath("$.status").value("enable"))
				.andReturn();

		Long roleId = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asLong();

		mockMvc.perform(
						put("/api/roles/{id}", roleId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(Map.of(
										"rolesId", 202,
										"rolesType", "manager",
										"status", "disable"
								)))
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(roleId))
				.andExpect(jsonPath("$.rolesId").value(202))
				.andExpect(jsonPath("$.rolesType").value("manager"))
				.andExpect(jsonPath("$.status").value("disable"));

		mockMvc.perform(
						delete("/api/roles/{id}", roleId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Role deleted successfully"));

		mockMvc.perform(
						get("/api/roles/{id}", roleId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				)
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Role not found with id: " + roleId));
	}

	@Test
	void tblUserCrudFlow() throws Exception {
		String email = "tbluser." + UUID.randomUUID() + "@example.com";
		String token = registerAndGetToken(email);

		MvcResult createResult = mockMvc.perform(
						post("/api/users")
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(Map.of(
										"username", "roeun.dary",
										"userpass", "Pass1234",
										"description", "merchant admin",
										"role_type", "admin",
										"status", "enable",
										"sessions", "active",
										"image", "profile.png"
								)))
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.username").value("roeun.dary"))
				.andExpect(jsonPath("$.description").value("merchant admin"))
				.andExpect(jsonPath("$.roleType").value("admin"))
				.andExpect(jsonPath("$.status").value("enable"))
				.andExpect(jsonPath("$.sessions").value("active"))
				.andExpect(jsonPath("$.image").value("profile.png"))
				.andExpect(jsonPath("$.createDate").isString())
				.andReturn();

		Integer userId = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asInt();
		TblUser createdUser = tblUserRepository.findById(userId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertNotEquals("Pass1234", createdUser.getUserpass());
		org.junit.jupiter.api.Assertions.assertTrue(passwordEncoder.matches("Pass1234", createdUser.getUserpass()));

		mockMvc.perform(
						put("/api/users/{id}", userId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(Map.of(
										"username", "roeun.updated",
										"userpass", "UpdatedPass123",
										"description", "merchant manager",
										"role_type", "manager",
										"status", "disable",
										"sessions", "inactive",
										"image", "updated.png"
								)))
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(userId))
				.andExpect(jsonPath("$.username").value("roeun.updated"))
				.andExpect(jsonPath("$.description").value("merchant manager"))
				.andExpect(jsonPath("$.roleType").value("manager"))
				.andExpect(jsonPath("$.status").value("disable"))
				.andExpect(jsonPath("$.sessions").value("inactive"))
				.andExpect(jsonPath("$.image").value("updated.png"));

		TblUser updatedUser = tblUserRepository.findById(userId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertNotEquals("UpdatedPass123", updatedUser.getUserpass());
		org.junit.jupiter.api.Assertions.assertTrue(passwordEncoder.matches("UpdatedPass123", updatedUser.getUserpass()));

		mockMvc.perform(
						delete("/api/users/{id}", userId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("User deleted successfully"));

		mockMvc.perform(
						get("/api/users/{id}", userId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				)
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("User not found with id: " + userId));
	}

	@Test
	void tblUserAllowsNullImage() throws Exception {
		String email = "tbluser.nullimage." + UUID.randomUUID() + "@example.com";
		String token = registerAndGetToken(email);

		String createPayload = """
				{
				  "username": "null.image",
				  "userpass": "Pass1234",
				  "description": "test null image",
				  "role_type": "admin",
				  "status": "enable",
				  "sessions": "active",
				  "image": null
				}
				""";

		MvcResult createResult = mockMvc.perform(
						post("/api/users")
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content(createPayload)
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.username").value("null.image"))
				.andExpect(jsonPath("$.image").doesNotExist())
				.andReturn();

		Integer userId = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asInt();
		TblUser createdUser = tblUserRepository.findById(userId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertNull(createdUser.getImage());

		String updatePayload = """
				{
				  "username": "null.image.updated",
				  "userpass": "UpdatedPass123",
				  "description": "test null image update",
				  "role_type": "manager",
				  "status": "disable",
				  "sessions": "inactive",
				  "image": null
				}
				""";

		mockMvc.perform(
						put("/api/users/{id}", userId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content(updatePayload)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("null.image.updated"))
				.andExpect(jsonPath("$.image").doesNotExist());

		TblUser updatedUser = tblUserRepository.findById(userId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertNull(updatedUser.getImage());
	}

	@Test
	void tblUserRejectsInvalidSessions() throws Exception {
		String email = "tbluser.invalidsessions." + UUID.randomUUID() + "@example.com";
		String token = registerAndGetToken(email);

		mockMvc.perform(
						post("/api/users")
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content("""
										{
										  "username": "invalid.sessions",
										  "userpass": "Pass1234",
										  "description": "invalid sessions",
										  "role_type": "admin",
										  "status": "enable",
										  "sessions": "web"
										}
										""")
				)
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("sessions: sessions must be either active or inactive"));
	}

	@Test
	void parentMerchantCrudFlow() throws Exception {
		String email = "parentmerchant." + UUID.randomUUID() + "@example.com";
		String token = registerAndGetToken(email);

		MvcResult createResult = mockMvc.perform(
						post("/api/parent-merchants")
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content("""
										{
										  "merchantId": "PM0001",
										  "merchant_pass": "ParentPass123",
										  "name": "Parent Merchant",
										  "parent_name": "Main Parent",
										  "location": "Phnom Penh",
										  "email": "parent.merchant@example.com",
										  "mobileNumber": "012345678",
										  "status": "enable",
										  "sessions": "active",
										  "log_time": 10,
										  "image": "parent.png"
										}
										""")
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.merchantId").value("PM0001"))
				.andExpect(jsonPath("$.name").value("Parent Merchant"))
				.andExpect(jsonPath("$.parentName").value("Main Parent"))
				.andExpect(jsonPath("$.location").value("Phnom Penh"))
				.andExpect(jsonPath("$.email").value("parent.merchant@example.com"))
				.andExpect(jsonPath("$.mobileNumber").value("012345678"))
				.andExpect(jsonPath("$.status").value("enable"))
				.andExpect(jsonPath("$.sessions").value("active"))
				.andExpect(jsonPath("$.logTime").value(10))
				.andExpect(jsonPath("$.image").value("parent.png"))
				.andExpect(jsonPath("$.createDate").isString())
				.andReturn();

		Integer merchantId = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asInt();
		ParentMerchant createdMerchant = parentMerchantRepository.findById(merchantId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertEquals("PM0001", createdMerchant.getMerchantId());
		org.junit.jupiter.api.Assertions.assertEquals("PM0001", createdMerchant.getMerchantIdCopy());
		org.junit.jupiter.api.Assertions.assertEquals("012345678", createdMerchant.getMobileNumber());
		org.junit.jupiter.api.Assertions.assertEquals("012345678", createdMerchant.getMobileNumberCopy());
		org.junit.jupiter.api.Assertions.assertNotEquals("ParentPass123", createdMerchant.getMerchantPass());
		org.junit.jupiter.api.Assertions.assertTrue(passwordEncoder.matches("ParentPass123", createdMerchant.getMerchantPass()));

		mockMvc.perform(
						put("/api/parent-merchants/{id}", merchantId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content("""
										{
										  "merchant_id": "PM0002",
										  "merchant_pass": "UpdatedParent123",
										  "name": "Parent Merchant Updated",
										  "parent_name": "Backup Parent",
										  "location": "Siem Reap",
										  "email": "updated.parent@example.com",
										  "mobile_number": "098765432",
										  "status": "disable",
										  "sessions": "inactive",
										  "log_time": 20,
										  "image": null
										}
										""")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(merchantId))
				.andExpect(jsonPath("$.merchantId").value("PM0002"))
				.andExpect(jsonPath("$.name").value("Parent Merchant Updated"))
				.andExpect(jsonPath("$.parentName").value("Backup Parent"))
				.andExpect(jsonPath("$.location").value("Siem Reap"))
				.andExpect(jsonPath("$.email").value("updated.parent@example.com"))
				.andExpect(jsonPath("$.mobileNumber").value("098765432"))
				.andExpect(jsonPath("$.status").value("disable"))
				.andExpect(jsonPath("$.sessions").value("inactive"))
				.andExpect(jsonPath("$.logTime").value(20))
				.andExpect(jsonPath("$.image").doesNotExist());

		ParentMerchant updatedMerchant = parentMerchantRepository.findById(merchantId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertEquals("PM0002", updatedMerchant.getMerchantId());
		org.junit.jupiter.api.Assertions.assertEquals("PM0002", updatedMerchant.getMerchantIdCopy());
		org.junit.jupiter.api.Assertions.assertEquals("098765432", updatedMerchant.getMobileNumber());
		org.junit.jupiter.api.Assertions.assertEquals("098765432", updatedMerchant.getMobileNumberCopy());
		org.junit.jupiter.api.Assertions.assertNull(updatedMerchant.getImage());
		org.junit.jupiter.api.Assertions.assertNotEquals("UpdatedParent123", updatedMerchant.getMerchantPass());
		org.junit.jupiter.api.Assertions.assertTrue(passwordEncoder.matches("UpdatedParent123", updatedMerchant.getMerchantPass()));

		mockMvc.perform(
						delete("/api/parent-merchants/{id}", merchantId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Parent merchant deleted successfully"));

		mockMvc.perform(
						get("/api/parent-merchants/{id}", merchantId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				)
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Parent merchant not found with id: " + merchantId));
	}

	@Test
	void merchantCrudFlow() throws Exception {
		String email = "merchant." + UUID.randomUUID() + "@example.com";
		String token = registerAndGetToken(email);

		MvcResult createResult = mockMvc.perform(
						post("/api/merchants")
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content("""
										{
										  "merchantId": "M0001",
										  "merchant_pass": "MerchantPass123",
										  "username": "merchant.user",
										  "parent_merchant_name": "Main Parent Merchant",
										  "location": "Phnom Penh",
										  "mobileNumber": "011223344",
										  "status": "enable",
										  "sessions": "active",
										  "log_time": 15,
										  "image": "merchant.png"
										}
										""")
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.merchantId").value("M0001"))
				.andExpect(jsonPath("$.username").value("merchant.user"))
				.andExpect(jsonPath("$.parentMerchantName").value("Main Parent Merchant"))
				.andExpect(jsonPath("$.location").value("Phnom Penh"))
				.andExpect(jsonPath("$.mobileNumber").value("011223344"))
				.andExpect(jsonPath("$.status").value("enable"))
				.andExpect(jsonPath("$.sessions").value("active"))
				.andExpect(jsonPath("$.logTime").value(15))
				.andExpect(jsonPath("$.image").value("merchant.png"))
				.andExpect(jsonPath("$.createDate").isString())
				.andReturn();

		Integer merchantId = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asInt();
		Merchant createdMerchant = merchantRepository.findById(merchantId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertEquals("M0001", createdMerchant.getMerchantId());
		org.junit.jupiter.api.Assertions.assertEquals("M0001", createdMerchant.getMerchantIdCopy());
		org.junit.jupiter.api.Assertions.assertEquals("011223344", createdMerchant.getMobileNumber());
		org.junit.jupiter.api.Assertions.assertEquals("011223344", createdMerchant.getMobileNumberCopy());
		org.junit.jupiter.api.Assertions.assertNotEquals("MerchantPass123", createdMerchant.getMerchantPass());
		org.junit.jupiter.api.Assertions.assertTrue(passwordEncoder.matches("MerchantPass123", createdMerchant.getMerchantPass()));

		mockMvc.perform(
						put("/api/merchants/{id}", merchantId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content("""
										{
										  "merchant_id": "M0002",
										  "merchant_pass": "UpdatedMerchant123",
										  "username": "merchant.updated",
										  "parent_merchant_name": "Backup Parent Merchant",
										  "location": "Siem Reap",
										  "mobile_number": "099887766",
										  "status": "disable",
										  "sessions": "inactive",
										  "log_time": 30,
										  "image": null
										}
										""")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(merchantId))
				.andExpect(jsonPath("$.merchantId").value("M0002"))
				.andExpect(jsonPath("$.username").value("merchant.updated"))
				.andExpect(jsonPath("$.parentMerchantName").value("Backup Parent Merchant"))
				.andExpect(jsonPath("$.location").value("Siem Reap"))
				.andExpect(jsonPath("$.mobileNumber").value("099887766"))
				.andExpect(jsonPath("$.status").value("disable"))
				.andExpect(jsonPath("$.sessions").value("inactive"))
				.andExpect(jsonPath("$.logTime").value(30))
				.andExpect(jsonPath("$.image").doesNotExist());

		Merchant updatedMerchant = merchantRepository.findById(merchantId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertEquals("M0002", updatedMerchant.getMerchantId());
		org.junit.jupiter.api.Assertions.assertEquals("M0002", updatedMerchant.getMerchantIdCopy());
		org.junit.jupiter.api.Assertions.assertEquals("099887766", updatedMerchant.getMobileNumber());
		org.junit.jupiter.api.Assertions.assertEquals("099887766", updatedMerchant.getMobileNumberCopy());
		org.junit.jupiter.api.Assertions.assertNull(updatedMerchant.getImage());
		org.junit.jupiter.api.Assertions.assertNotEquals("UpdatedMerchant123", updatedMerchant.getMerchantPass());
		org.junit.jupiter.api.Assertions.assertTrue(passwordEncoder.matches("UpdatedMerchant123", updatedMerchant.getMerchantPass()));

		mockMvc.perform(
						delete("/api/merchants/{id}", merchantId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Merchant deleted successfully"));

		mockMvc.perform(
						get("/api/merchants/{id}", merchantId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				)
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Merchant not found with id: " + merchantId));
	}

	@Test
	void merchantInfoCrudFlow() throws Exception {
		String email = "merchantinfo." + UUID.randomUUID() + "@example.com";
		String token = registerAndGetToken(email);

		MvcResult createResult = mockMvc.perform(
						post("/api/merchant-info")
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content("""
										{
										  "merchant_id": "MI0001",
										  "parent_merchant_name": "Main Parent Merchant",
										  "sub_merchant_name": "Sub Merchant A",
										  "contact_person": "John Doe",
										  "email": "merchant.info@example.com",
										  "website": "https://example.com",
										  "contact": "012345678",
										  "business_license": "BL-100",
										  "business_type": "Retail",
										  "mcc_code": "5411",
										  "transaction_currency": "USD",
										  "erwp": "ERWP001",
										  "account_number": "1234567890",
										  "qr": "qr-data",
										  "tg_id": "@merchantinfo",
										  "revers_code": "REV01",
										  "ewp": "EWP001",
										  "image": "merchant-info.png"
										}
										""")
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.merchantId").value("MI0001"))
				.andExpect(jsonPath("$.parentMerchantName").value("Main Parent Merchant"))
				.andExpect(jsonPath("$.subMerchantName").value("Sub Merchant A"))
				.andExpect(jsonPath("$.contactPerson").value("John Doe"))
				.andExpect(jsonPath("$.email").value("merchant.info@example.com"))
				.andExpect(jsonPath("$.website").value("https://example.com"))
				.andExpect(jsonPath("$.contact").value("012345678"))
				.andExpect(jsonPath("$.businessLicense").value("BL-100"))
				.andExpect(jsonPath("$.businessType").value("Retail"))
				.andExpect(jsonPath("$.mccCode").value("5411"))
				.andExpect(jsonPath("$.transactionCurrency").value("USD"))
				.andExpect(jsonPath("$.erwp").value("ERWP001"))
				.andExpect(jsonPath("$.accountNumber").value("1234567890"))
				.andExpect(jsonPath("$.qr").value("qr-data"))
				.andExpect(jsonPath("$.tgId").value("@merchantinfo"))
				.andExpect(jsonPath("$.reversCode").value("REV01"))
				.andExpect(jsonPath("$.ewp").value("EWP001"))
				.andExpect(jsonPath("$.image").value("merchant-info.png"))
				.andReturn();

		Integer merchantInfoId = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asInt();
		MerchantInfo createdMerchantInfo = merchantInfoRepository.findById(merchantInfoId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertEquals("MI0001", createdMerchantInfo.getMerchantId());
		org.junit.jupiter.api.Assertions.assertEquals("Sub Merchant A", createdMerchantInfo.getSubMerchantName());
		org.junit.jupiter.api.Assertions.assertEquals("merchant-info.png", createdMerchantInfo.getImage());

		mockMvc.perform(
						put("/api/merchant-info/{id}", merchantInfoId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content("""
										{
										  "merchant_id": "MI0002",
										  "Parent_Merchant_Name": "Backup Parent Merchant",
										  "sub_merchant_name": "Sub Merchant B",
										  "Contact_Person": "Jane Doe",
										  "Email": "updated.merchant.info@example.com",
										  "Website": "https://updated.example.com",
										  "Contact": "098765432",
										  "Business_License": "BL-200",
										  "Business_Type": "Services",
										  "mcc_code": "5812",
										  "Transaction_Currency": "KHR",
										  "erwp": "ERWP002",
										  "Account_Number": "999888777",
										  "QR": "updated-qr-data",
										  "tgID": "@updatedmerchantinfo",
										  "revers_code": "REV02",
										  "ewp": "EWP002",
										  "image": null
										}
										""")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(merchantInfoId))
				.andExpect(jsonPath("$.merchantId").value("MI0002"))
				.andExpect(jsonPath("$.parentMerchantName").value("Backup Parent Merchant"))
				.andExpect(jsonPath("$.subMerchantName").value("Sub Merchant B"))
				.andExpect(jsonPath("$.contactPerson").value("Jane Doe"))
				.andExpect(jsonPath("$.email").value("updated.merchant.info@example.com"))
				.andExpect(jsonPath("$.website").value("https://updated.example.com"))
				.andExpect(jsonPath("$.contact").value("098765432"))
				.andExpect(jsonPath("$.businessLicense").value("BL-200"))
				.andExpect(jsonPath("$.businessType").value("Services"))
				.andExpect(jsonPath("$.mccCode").value("5812"))
				.andExpect(jsonPath("$.transactionCurrency").value("KHR"))
				.andExpect(jsonPath("$.erwp").value("ERWP002"))
				.andExpect(jsonPath("$.accountNumber").value("999888777"))
				.andExpect(jsonPath("$.qr").value("updated-qr-data"))
				.andExpect(jsonPath("$.tgId").value("@updatedmerchantinfo"))
				.andExpect(jsonPath("$.reversCode").value("REV02"))
				.andExpect(jsonPath("$.ewp").value("EWP002"))
				.andExpect(jsonPath("$.image").doesNotExist());

		MerchantInfo updatedMerchantInfo = merchantInfoRepository.findById(merchantInfoId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertEquals("MI0002", updatedMerchantInfo.getMerchantId());
		org.junit.jupiter.api.Assertions.assertEquals("Sub Merchant B", updatedMerchantInfo.getSubMerchantName());
		org.junit.jupiter.api.Assertions.assertNull(updatedMerchantInfo.getImage());

		mockMvc.perform(
						delete("/api/merchant-info/{id}", merchantInfoId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Merchant info deleted successfully"));

		mockMvc.perform(
						get("/api/merchant-info/{id}", merchantInfoId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				)
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Merchant info not found with id: " + merchantInfoId));
	}

	@Test
	void merchantInfoAllowsNullQrAndEmptyEwp() throws Exception {
		String email = "merchantinfo.nullqr." + UUID.randomUUID() + "@example.com";
		String token = registerAndGetToken(email);

		MvcResult createResult = mockMvc.perform(
						post("/api/merchant-info")
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content("""
										{
										  "merchant_id": "MI0101",
										  "parent_merchant_name": "Parent Merchant",
										  "sub_merchant_name": "Sub Merchant",
										  "contact_person": "John Doe",
										  "email": "null.qr@example.com",
										  "website": "https://example.com",
										  "contact": "012345678",
										  "business_license": "BL-101",
										  "business_type": "Retail",
										  "mcc_code": "5411",
										  "transaction_currency": "USD",
										  "erwp": "ERWP101",
										  "account_number": "1234567890",
										  "qr": null,
										  "tg_id": "@nullqr",
										  "revers_code": "REV10",
										  "ewp": "",
										  "image": null
										}
										""")
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.merchantId").value("MI0101"))
				.andExpect(jsonPath("$.qr").doesNotExist())
				.andExpect(jsonPath("$.ewp").value(""))
				.andReturn();

		Integer merchantInfoId = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asInt();
		MerchantInfo createdMerchantInfo = merchantInfoRepository.findById(merchantInfoId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertNull(createdMerchantInfo.getQr());
		org.junit.jupiter.api.Assertions.assertEquals("", createdMerchantInfo.getEwp());

		mockMvc.perform(
						put("/api/merchant-info/{id}", merchantInfoId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content("""
										{
										  "merchant_id": "MI0102",
										  "parent_merchant_name": "Parent Merchant Updated",
										  "sub_merchant_name": "Sub Merchant Updated",
										  "contact_person": "Jane Doe",
										  "email": "updated.null.qr@example.com",
										  "website": "https://updated.example.com",
										  "contact": "098765432",
										  "business_license": "BL-102",
										  "business_type": "Services",
										  "mcc_code": "5812",
										  "transaction_currency": "KHR",
										  "erwp": "ERWP102",
										  "account_number": "999888777",
										  "qr": null,
										  "tg_id": "@updatednullqr",
										  "revers_code": "REV11",
										  "ewp": "",
										  "image": null
										}
										""")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.merchantId").value("MI0102"))
				.andExpect(jsonPath("$.qr").doesNotExist())
				.andExpect(jsonPath("$.ewp").value(""));

		MerchantInfo updatedMerchantInfo = merchantInfoRepository.findById(merchantInfoId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertNull(updatedMerchantInfo.getQr());
		org.junit.jupiter.api.Assertions.assertEquals("", updatedMerchantInfo.getEwp());
	}

	@Test
	void khqrLinkAccCrudFlow() throws Exception {
		String email = "khqrlink." + UUID.randomUUID() + "@example.com";
		String token = registerAndGetToken(email);

		MvcResult createResult = mockMvc.perform(
						post("/api/khqr-link-accounts")
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content("""
										{
										  "merchantId": "KHQR001",
										  "account": "001122334455",
										  "ccy": "USD",
										  "merchantName": "KHQR Merchant",
										  "merchantBranch": "Main Branch",
										  "image": null
										}
										""")
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.merchantId").value("KHQR001"))
				.andExpect(jsonPath("$.account").value("001122334455"))
				.andExpect(jsonPath("$.ccy").value("USD"))
				.andExpect(jsonPath("$.merchantName").value("KHQR Merchant"))
				.andExpect(jsonPath("$.merchantBranch").value("Main Branch"))
				.andExpect(jsonPath("$.merchant_branch").value(""))
				.andExpect(jsonPath("$.merchant_id").value(""))
				.andExpect(jsonPath("$.merchant_name").value(""))
				.andExpect(jsonPath("$.image").doesNotExist())
				.andReturn();

		Integer khqrId = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asInt();
		KhqrLinkAcc createdKhqrLinkAcc = khqrLinkAccRepository.findById(khqrId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertEquals("", createdKhqrLinkAcc.getMerchant_branch());
		org.junit.jupiter.api.Assertions.assertEquals("", createdKhqrLinkAcc.getMerchant_id());
		org.junit.jupiter.api.Assertions.assertEquals("", createdKhqrLinkAcc.getMerchant_name());

		mockMvc.perform(
						put("/api/khqr-link-accounts/{id}", khqrId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content("""
										{
										  "merchantId": "KHQR002",
										  "account": "998877665544",
										  "ccy": "KHR",
										  "merchantName": "KHQR Merchant Updated",
										  "merchantBranch": "Branch Updated",
										  "merchant_branch": "",
										  "merchant_id": "",
										  "merchant_name": "",
										  "image": "khqr.png"
										}
										""")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(khqrId))
				.andExpect(jsonPath("$.merchantId").value("KHQR002"))
				.andExpect(jsonPath("$.account").value("998877665544"))
				.andExpect(jsonPath("$.ccy").value("KHR"))
				.andExpect(jsonPath("$.merchantName").value("KHQR Merchant Updated"))
				.andExpect(jsonPath("$.merchantBranch").value("Branch Updated"))
				.andExpect(jsonPath("$.merchant_branch").value(""))
				.andExpect(jsonPath("$.merchant_id").value(""))
				.andExpect(jsonPath("$.merchant_name").value(""))
				.andExpect(jsonPath("$.image").value("khqr.png"));

		KhqrLinkAcc updatedKhqrLinkAcc = khqrLinkAccRepository.findById(khqrId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertEquals("", updatedKhqrLinkAcc.getMerchant_branch());
		org.junit.jupiter.api.Assertions.assertEquals("", updatedKhqrLinkAcc.getMerchant_id());
		org.junit.jupiter.api.Assertions.assertEquals("", updatedKhqrLinkAcc.getMerchant_name());
		org.junit.jupiter.api.Assertions.assertEquals("khqr.png", updatedKhqrLinkAcc.getImage());

		mockMvc.perform(
						delete("/api/khqr-link-accounts/{id}", khqrId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("KHQR link account deleted successfully"));

		mockMvc.perform(
						get("/api/khqr-link-accounts/{id}", khqrId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				)
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("KHQR link account not found with id: " + khqrId));
	}

	@Test
	void checkoutCrudFlow() throws Exception {
		String email = "checkout." + UUID.randomUUID() + "@example.com";
		String token = registerAndGetToken(email);

		MvcResult createResult = mockMvc.perform(
						post("/api/checkouts")
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content("""
										{
										  "merchantID": "CO0001",
										  "name": "Checkout Merchant",
										  "location": "Phnom Penh",
										  "amt": "25.50",
										  "mdOrder": "ORDER001",
										  "QRcode_url": "https://example.com/qr/1",
										  "currency": "USD",
										  "mobileNumber": "012345678",
										  "checkout_date": "2026-04-13",
										  "returnUrl": "https://example.com/return",
										  "image": null
										}
										""")
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.merchantId").value("CO0001"))
				.andExpect(jsonPath("$.name").value("Checkout Merchant"))
				.andExpect(jsonPath("$.location").value("Phnom Penh"))
				.andExpect(jsonPath("$.amt").value("25.50"))
				.andExpect(jsonPath("$.mdOrder").value("ORDER001"))
				.andExpect(jsonPath("$.qrCodeUrl").value("https://example.com/qr/1"))
				.andExpect(jsonPath("$.currency").value("USD"))
				.andExpect(jsonPath("$.mobileNumber").value("012345678"))
				.andExpect(jsonPath("$.checkoutDate").value("2026-04-13"))
				.andExpect(jsonPath("$.returnUrl").value("https://example.com/return"))
				.andExpect(jsonPath("$.continueSuccessUrl").value(""))
				.andExpect(jsonPath("$.session_token").value(""))
				.andExpect(jsonPath("$.create_by").value(""))
				.andExpect(jsonPath("$.continue_successful_url").value(""))
				.andExpect(jsonPath("$.md_order").value(""))
				.andExpect(jsonPath("$.mobile_number").value(""))
				.andExpect(jsonPath("$.return_url").value(""))
				.andExpect(jsonPath("$.image").doesNotExist())
				.andReturn();

		Integer checkoutId = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asInt();
		Checkout createdCheckout = checkoutRepository.findById(checkoutId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertEquals("", createdCheckout.getContinueSuccessUrl());
		org.junit.jupiter.api.Assertions.assertEquals("", createdCheckout.getSessionToken());
		org.junit.jupiter.api.Assertions.assertEquals("", createdCheckout.getCreateBy());
		org.junit.jupiter.api.Assertions.assertEquals("", createdCheckout.getContinue_successful_url());
		org.junit.jupiter.api.Assertions.assertEquals("", createdCheckout.getMd_order());
		org.junit.jupiter.api.Assertions.assertEquals("", createdCheckout.getMobile_number());
		org.junit.jupiter.api.Assertions.assertEquals("", createdCheckout.getReturn_url());

		mockMvc.perform(
						put("/api/checkouts/{id}", checkoutId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content("""
										{
										  "merchantID": "CO0002",
										  "name": "Checkout Merchant Updated",
										  "location": "Siem Reap",
										  "amt": "99.99",
										  "mdOrder": "ORDER002",
										  "QRcode_url": "https://example.com/qr/2",
										  "currency": "KHR",
										  "mobileNumber": "098765432",
										  "checkout_date": "2026-04-14",
										  "returnUrl": "https://example.com/return-updated",
										  "continueSuccessUrl": "",
										  "session_token": "",
										  "create_by": "",
										  "continue_successful_url": "",
										  "md_order": "",
										  "mobile_number": "",
										  "return_url": "",
										  "image": "checkout.png"
										}
										""")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(checkoutId))
				.andExpect(jsonPath("$.merchantId").value("CO0002"))
				.andExpect(jsonPath("$.name").value("Checkout Merchant Updated"))
				.andExpect(jsonPath("$.location").value("Siem Reap"))
				.andExpect(jsonPath("$.amt").value("99.99"))
				.andExpect(jsonPath("$.mdOrder").value("ORDER002"))
				.andExpect(jsonPath("$.qrCodeUrl").value("https://example.com/qr/2"))
				.andExpect(jsonPath("$.currency").value("KHR"))
				.andExpect(jsonPath("$.mobileNumber").value("098765432"))
				.andExpect(jsonPath("$.checkoutDate").value("2026-04-14"))
				.andExpect(jsonPath("$.returnUrl").value("https://example.com/return-updated"))
				.andExpect(jsonPath("$.continueSuccessUrl").value(""))
				.andExpect(jsonPath("$.session_token").value(""))
				.andExpect(jsonPath("$.create_by").value(""))
				.andExpect(jsonPath("$.continue_successful_url").value(""))
				.andExpect(jsonPath("$.md_order").value(""))
				.andExpect(jsonPath("$.mobile_number").value(""))
				.andExpect(jsonPath("$.return_url").value(""))
				.andExpect(jsonPath("$.image").value("checkout.png"));

		Checkout updatedCheckout = checkoutRepository.findById(checkoutId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertEquals("", updatedCheckout.getContinueSuccessUrl());
		org.junit.jupiter.api.Assertions.assertEquals("", updatedCheckout.getSessionToken());
		org.junit.jupiter.api.Assertions.assertEquals("", updatedCheckout.getCreateBy());
		org.junit.jupiter.api.Assertions.assertEquals("", updatedCheckout.getContinue_successful_url());
		org.junit.jupiter.api.Assertions.assertEquals("", updatedCheckout.getMd_order());
		org.junit.jupiter.api.Assertions.assertEquals("", updatedCheckout.getMobile_number());
		org.junit.jupiter.api.Assertions.assertEquals("", updatedCheckout.getReturn_url());
		org.junit.jupiter.api.Assertions.assertEquals("checkout.png", updatedCheckout.getImage());

		mockMvc.perform(
						delete("/api/checkouts/{id}", checkoutId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Checkout deleted successfully"));

		mockMvc.perform(
						get("/api/checkouts/{id}", checkoutId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				)
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Checkout not found with id: " + checkoutId));
	}

	@Test
	void incommingTranxCrudFlow() throws Exception {
		String email = "incommingtranx." + UUID.randomUUID() + "@example.com";
		String token = registerAndGetToken(email);

		MvcResult createResult = mockMvc.perform(
						post("/api/incomming-tranx")
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content("""
										{
										  "mid": "329808",
										  "src_account_id": "2120256666",
										  "fccref": "010BKIN23031BA7T",
										  "merchant_id": "LE00000016",
										  "amount": "5",
										  "currency": "USD",
										  "create_time": "2026-04-13 09:30:08",
										  "trx_hash": "87980b872791c485867482c17ed2816fd4da1d1568ea31345626858680be0cc9",
										  "payment_type": "KHQR",
										  "trn_type": "SALE",
										  "appr_code": "",
										  "status": "SUCCESS",
										  "storeName": "LARRYTA BUS",
										  "terminalLabel": "LARRYTA BUS",
										  "customer_name": "KONG PECH DALEN / CHIN SOPANHA",
										  "trx_short_hash": "87980b87",
										  "billNumber": "CAB660510792",
										  "create_date": "2026-04-13 09:31:44",
										  "sale_draft_no": "18351",
										  "bill_number": null,
										  "store_name": null,
										  "terminal_label": null,
										  "image": null
										}
										""")
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.mid").value("329808"))
				.andExpect(jsonPath("$.srcAccountId").value("2120256666"))
				.andExpect(jsonPath("$.fccref").value("010BKIN23031BA7T"))
				.andExpect(jsonPath("$.merchantId").value("LE00000016"))
				.andExpect(jsonPath("$.amount").value("5"))
				.andExpect(jsonPath("$.currency").value("USD"))
				.andExpect(jsonPath("$.createTime").value("2026-04-13 09:30:08"))
				.andExpect(jsonPath("$.trxHash").value("87980b872791c485867482c17ed2816fd4da1d1568ea31345626858680be0cc9"))
				.andExpect(jsonPath("$.paymentType").value("KHQR"))
				.andExpect(jsonPath("$.trnType").value("SALE"))
				.andExpect(jsonPath("$.apprCode").value(""))
				.andExpect(jsonPath("$.status").value("SUCCESS"))
				.andExpect(jsonPath("$.storeName").value("LARRYTA BUS"))
				.andExpect(jsonPath("$.terminalLabel").value("LARRYTA BUS"))
				.andExpect(jsonPath("$.customerName").value("KONG PECH DALEN / CHIN SOPANHA"))
				.andExpect(jsonPath("$.trxShortHash").value("87980b87"))
				.andExpect(jsonPath("$.billNumber").value("CAB660510792"))
				.andExpect(jsonPath("$.createDate").value("2026-04-13 09:31:44"))
				.andExpect(jsonPath("$.saleDraftNo").value("18351"))
				.andExpect(jsonPath("$.bill_number").doesNotExist())
				.andExpect(jsonPath("$.store_name").doesNotExist())
				.andExpect(jsonPath("$.terminal_label").doesNotExist())
				.andExpect(jsonPath("$.image").doesNotExist())
				.andReturn();

		Integer incommingTranxId = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asInt();
		IncommingTranx createdIncommingTranx = incommingTranxRepository.findById(incommingTranxId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertNull(createdIncommingTranx.getBill_number());
		org.junit.jupiter.api.Assertions.assertNull(createdIncommingTranx.getStore_name());
		org.junit.jupiter.api.Assertions.assertNull(createdIncommingTranx.getTerminal_label());
		org.junit.jupiter.api.Assertions.assertNull(createdIncommingTranx.getImage());
		org.junit.jupiter.api.Assertions.assertEquals("", createdIncommingTranx.getApprCode());

		mockMvc.perform(
						put("/api/incomming-tranx/{id}", incommingTranxId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
								.contentType(MediaType.APPLICATION_JSON)
								.content("""
										{
										  "mid": "405676",
										  "src_account_id": "0000012728",
										  "fccref": "010BKOU23031C9QN",
										  "merchant_id": "NW00000133",
										  "amount": "100",
										  "currency": "KHR",
										  "create_time": "2026-04-14 15:22:32",
										  "trx_hash": "d55b3c6697",
										  "payment_type": "KHQR",
										  "trn_type": "SALE",
										  "appr_code": "U168",
										  "status": "SUCCESS",
										  "storeName": "NAGA WORLD2",
										  "terminalLabel": "NAGA WORLD TEST",
										  "customer_name": "MOEUN NARIN",
										  "trx_short_hash": "d55b3c66",
										  "billNumber": "13245335",
										  "create_date": "2026-04-14 15:24:05",
										  "sale_draft_no": "18234",
										  "bill_number": "13245335",
										  "store_name": "NAGA WORLD2",
										  "terminal_label": "NAGA WORLD TEST",
										  "image": "incoming.png"
										}
										""")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(incommingTranxId))
				.andExpect(jsonPath("$.mid").value("405676"))
				.andExpect(jsonPath("$.srcAccountId").value("0000012728"))
				.andExpect(jsonPath("$.fccref").value("010BKOU23031C9QN"))
				.andExpect(jsonPath("$.merchantId").value("NW00000133"))
				.andExpect(jsonPath("$.amount").value("100"))
				.andExpect(jsonPath("$.currency").value("KHR"))
				.andExpect(jsonPath("$.createTime").value("2026-04-14 15:22:32"))
				.andExpect(jsonPath("$.trxHash").value("d55b3c6697"))
				.andExpect(jsonPath("$.paymentType").value("KHQR"))
				.andExpect(jsonPath("$.trnType").value("SALE"))
				.andExpect(jsonPath("$.apprCode").value("U168"))
				.andExpect(jsonPath("$.status").value("SUCCESS"))
				.andExpect(jsonPath("$.storeName").value("NAGA WORLD2"))
				.andExpect(jsonPath("$.terminalLabel").value("NAGA WORLD TEST"))
				.andExpect(jsonPath("$.customerName").value("MOEUN NARIN"))
				.andExpect(jsonPath("$.trxShortHash").value("d55b3c66"))
				.andExpect(jsonPath("$.billNumber").value("13245335"))
				.andExpect(jsonPath("$.createDate").value("2026-04-14 15:24:05"))
				.andExpect(jsonPath("$.saleDraftNo").value("18234"))
				.andExpect(jsonPath("$.bill_number").value("13245335"))
				.andExpect(jsonPath("$.store_name").value("NAGA WORLD2"))
				.andExpect(jsonPath("$.terminal_label").value("NAGA WORLD TEST"))
				.andExpect(jsonPath("$.image").value("incoming.png"));

		IncommingTranx updatedIncommingTranx = incommingTranxRepository.findById(incommingTranxId).orElseThrow();
		org.junit.jupiter.api.Assertions.assertEquals("13245335", updatedIncommingTranx.getBill_number());
		org.junit.jupiter.api.Assertions.assertEquals("NAGA WORLD2", updatedIncommingTranx.getStore_name());
		org.junit.jupiter.api.Assertions.assertEquals("NAGA WORLD TEST", updatedIncommingTranx.getTerminal_label());
		org.junit.jupiter.api.Assertions.assertEquals("incoming.png", updatedIncommingTranx.getImage());

		mockMvc.perform(
						delete("/api/incomming-tranx/{id}", incommingTranxId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Incoming transaction deleted successfully"));

		mockMvc.perform(
						get("/api/incomming-tranx/{id}", incommingTranxId)
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				)
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Incoming transaction not found with id: " + incommingTranxId));
	}

	private String readToken(MvcResult result) throws Exception {
		JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());
		return root.get("token").asText();
	}

	private String registerAndGetToken(String email) throws Exception {
		RegisterRequest registerRequest = new RegisterRequest("Role User", email, "Password123");
		MvcResult registerResult = mockMvc.perform(
						post("/api/auth/register")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(registerRequest))
				)
				.andExpect(status().isCreated())
				.andReturn();

		return readToken(registerResult);
	}

}
