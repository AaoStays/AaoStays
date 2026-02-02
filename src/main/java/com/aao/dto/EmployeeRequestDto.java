package com.aao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for creating and updating Employee records.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {

    private Long userId; // Required for creation (links to User)
    private String employeeRole; // e.g., "Customer Service Executive"
    private String employeeCode; // Unique employee identifier
    private LocalDate hireDate; // Date of joining
    private BigDecimal salary; // Employee salary
    private String department; // e.g., "Support", "Operations"
    private Long managerId; // Optional - ID of the manager (employee)
}
