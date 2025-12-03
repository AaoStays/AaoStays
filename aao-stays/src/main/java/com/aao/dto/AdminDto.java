package com.aao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    private Long adminId;
    private Long userId;
    private String adminFullName;
    private String email;
    private String phoneNumber;
    private String role;
    private List<String> permissions;
    private String department;
    private LocalDateTime createdAt;
}
