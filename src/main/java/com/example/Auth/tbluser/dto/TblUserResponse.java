package com.example.Auth.tbluser.dto;

public record TblUserResponse(
        Integer id,
        String username,
        String description,
        String roleType,
        String status,
        String sessions,
        String image,
        String createDate
) {
}
