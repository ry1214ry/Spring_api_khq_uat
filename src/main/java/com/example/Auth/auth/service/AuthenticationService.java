package com.example.Auth.auth.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Auth.auth.dto.AuthResponse;
import com.example.Auth.auth.dto.LoginRequest;
import com.example.Auth.auth.dto.RegisterRequest;
import com.example.Auth.auth.dto.UserResponse;
import com.example.Auth.common.dto.MessageResponse;
import com.example.Auth.common.exception.EmailAlreadyExistsException;
import com.example.Auth.common.exception.InvalidTokenException;
import com.example.Auth.security.JwtService;
import com.example.Auth.token.model.Token;
import com.example.Auth.token.model.TokenType;
import com.example.Auth.token.repository.TokenRepository;
import com.example.Auth.user.model.AppUser;
import com.example.Auth.user.model.Role;
import com.example.Auth.user.repository.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            TokenRepository tokenRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new EmailAlreadyExistsException("Email is already registered");
        }

        AppUser user = new AppUser(
                request.name().trim(),
                normalizedEmail,
                deriveUsername(normalizedEmail),
                passwordEncoder.encode(request.password()),
                Role.USER,
                true
        );

        AppUser savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(savedUser);
        saveUserToken(savedUser, jwtToken);

        return buildAuthResponse(savedUser, jwtToken);
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(normalizedEmail, request.password())
        );

        AppUser user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        revokeAllUserTokens(user);
        String jwtToken = jwtService.generateToken(user);
        saveUserToken(user, jwtToken);

        return buildAuthResponse(user, jwtToken);
    }

    @Transactional
    public MessageResponse logout(String authorizationHeader) {
        String tokenValue = extractBearerToken(authorizationHeader);

        Token storedToken = tokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new InvalidTokenException("Token is invalid or already removed"));

        storedToken.setExpired(true);
        storedToken.setRevoked(true);
        tokenRepository.save(storedToken);

        return new MessageResponse("Logged out successfully");
    }

    public UserResponse currentUser(AppUser user) {
        return toUserResponse(user);
    }

    private AuthResponse buildAuthResponse(AppUser user, String token) {
        return new AuthResponse(token, "Bearer", toUserResponse(user));
    }

    private UserResponse toUserResponse(AppUser user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAppUsername(),
                user.getRole().name(),
                user.getEnabled()
        );
    }

    private String deriveUsername(String email) {
        String localPart = email.substring(0, email.indexOf('@'));
        String sanitized = localPart.replaceAll("[^a-zA-Z0-9._-]", "_");
        if (sanitized.isBlank()) {
            return "user";
        }
        return sanitized.length() > 50 ? sanitized.substring(0, 50) : sanitized;
    }

    private void saveUserToken(AppUser user, String jwtToken) {
        Token token = new Token();
        token.setUser(user);
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(AppUser user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUserId(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Authorization header must contain a Bearer token");
        }
        return authorizationHeader.substring(7);
    }
}
