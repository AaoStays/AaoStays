package com.aao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for employee attendance tracking.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDto {

    private Long employeeId;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime; // Optional - null if only checking in
    private String notes; // Optional notes
}
