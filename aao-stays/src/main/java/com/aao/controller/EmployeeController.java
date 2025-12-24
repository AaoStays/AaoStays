package com.aao.controller;

import com.aao.dto.AttendanceDto;
import com.aao.dto.EmployeeDto;
import com.aao.dto.EmployeeRequestDto;
import com.aao.dto.EmployeeTransferDto;
import com.aao.response.ApiResponse;
import com.aao.serviceInterface.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Employee Management API.
 * Handles all employee-related operations.
 */
@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // 1. Create Employee - ADMIN ONLY
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeDto>> createEmployee(
            @RequestBody EmployeeRequestDto employeeRequestDto) {
        ApiResponse<EmployeeDto> response = employeeService.createEmployee(employeeRequestDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }


    @GetMapping("/{employeeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<EmployeeDto>> getEmployeeById(@PathVariable Long employeeId) {
        ApiResponse<EmployeeDto> response = employeeService.getEmployeeById(employeeId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> getAllEmployees(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Boolean status) {
        ApiResponse<List<EmployeeDto>> response = employeeService.getAllEmployees(department, role, status);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // 4. Update Employee - ADMIN ONLY
    @PutMapping("/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeDto>> updateEmployee(
            @PathVariable Long employeeId,
            @RequestBody EmployeeRequestDto employeeRequestDto) {
        ApiResponse<EmployeeDto> response = employeeService.updateEmployee(employeeId, employeeRequestDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // 5. Delete Employee - ADMIN ONLY
    @DeleteMapping("/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long employeeId) {
        ApiResponse<Void> response = employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // 6. Record Attendance - ADMIN or EMPLOYEE can record
    @PostMapping("/{employeeId}/attendance")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<Void>> recordAttendance(
            @PathVariable Long employeeId,
            @RequestBody AttendanceDto attendanceDto) {
        // Ensure employeeId from path matches the one in DTO
        attendanceDto.setEmployeeId(employeeId);
        ApiResponse<Void> response = employeeService.recordAttendance(attendanceDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // 7. Activate Employee - ADMIN ONLY
    @PatchMapping("/{employeeId}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> activateEmployee(@PathVariable Long employeeId) {
        ApiResponse<Void> response = employeeService.activateEmployee(employeeId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // 8. Deactivate Employee - ADMIN ONLY
    @PatchMapping("/{employeeId}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deactivateEmployee(@PathVariable Long employeeId) {
        ApiResponse<Void> response = employeeService.deactivateEmployee(employeeId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // 9. Transfer Employee - ADMIN ONLY
    @PatchMapping("/{employeeId}/transfer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeDto>> transferEmployee(
            @PathVariable Long employeeId,
            @RequestBody EmployeeTransferDto transferDto) {
        ApiResponse<EmployeeDto> response = employeeService.transferEmployee(employeeId, transferDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}
