package com.aao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for employee department/role transfers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeTransferDto {

    private String newDepartment; // New department name
    private String newRole; // New role/designation
    private Long newManagerId; // Optional - new manager ID
}
