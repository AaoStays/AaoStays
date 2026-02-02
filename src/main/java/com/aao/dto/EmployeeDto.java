package com.aao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Employee entity.
 * Contains employee information along with related User data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private Long employeeId;
    private Long userId;

    // User information
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;

    // Employee specific information
    private String employeeRole;
    private String employeeCode;
    private LocalDate hireDate;
    private BigDecimal salary;
    private String department;

    // Manager information
    private Long managerId;
    private String managerName;

    // Status
    private Boolean isActive;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
