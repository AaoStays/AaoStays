package com.aao.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdminRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    private String adminFullName;
    private String email;
    private String phoneNumber;

    @NotNull(message = "Role is required")
    private String role;

    private List<String> permissions;
    private String department;
}
