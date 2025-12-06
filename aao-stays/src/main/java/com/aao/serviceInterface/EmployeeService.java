package com.aao.serviceInterface;

import com.aao.dto.AttendanceDto;
import com.aao.dto.EmployeeDto;
import com.aao.dto.EmployeeRequestDto;
import com.aao.dto.EmployeeTransferDto;
import com.aao.response.ApiResponse;

import java.util.List;

/**
 * Service interface for Employee management operations.
 */
public interface EmployeeService {

    /**
     * Creates a new employee linked to an existing User.
     * 
     * @param employeeRequestDto Employee creation details
     * @return ApiResponse containing created employee data
     */
    ApiResponse<EmployeeDto> createEmployee(EmployeeRequestDto employeeRequestDto);

    /**
     * Retrieves employee details by employee ID.
     * 
     * @param employeeId Employee ID
     * @return ApiResponse containing employee data
     */
    ApiResponse<EmployeeDto> getEmployeeById(Long employeeId);

    /**
     * Retrieves all employees with optional filters.
     * 
     * @param department Optional department filter
     * @param role       Optional role filter
     * @param isActive   Optional active status filter
     * @return ApiResponse containing list of employees
     */
    ApiResponse<List<EmployeeDto>> getAllEmployees(String department, String role, Boolean isActive);

    /**
     * Updates employee information.
     * 
     * @param employeeId         Employee ID
     * @param employeeRequestDto Updated employee details
     * @return ApiResponse containing updated employee data
     */
    ApiResponse<EmployeeDto> updateEmployee(Long employeeId, EmployeeRequestDto employeeRequestDto);

    /**
     * Deletes/removes an employee.
     * 
     * @param employeeId Employee ID
     * @return ApiResponse with success/failure status
     */
    ApiResponse<Void> deleteEmployee(Long employeeId);

    /**
     * Records employee attendance (check-in/check-out).
     * 
     * @param attendanceDto Attendance details
     * @return ApiResponse with success/failure status
     */
    ApiResponse<Void> recordAttendance(AttendanceDto attendanceDto);

    /**
     * Activates an employee account.
     * 
     * @param employeeId Employee ID
     * @return ApiResponse with success/failure status
     */
    ApiResponse<Void> activateEmployee(Long employeeId);

    /**
     * Deactivates an employee account.
     * 
     * @param employeeId Employee ID
     * @return ApiResponse with success/failure status
     */
    ApiResponse<Void> deactivateEmployee(Long employeeId);

    /**
     * Transfers employee to new department/role.
     * 
     * @param employeeId  Employee ID
     * @param transferDto Transfer details
     * @return ApiResponse containing updated employee data
     */
    ApiResponse<EmployeeDto> transferEmployee(Long employeeId, EmployeeTransferDto transferDto);
}
