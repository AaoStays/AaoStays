package com.aao.serviceImpl;

import com.aao.dto.AttendanceDto;
import com.aao.dto.EmployeeDto;
import com.aao.dto.EmployeeRequestDto;
import com.aao.dto.EmployeeTransferDto;
import com.aao.entity.Employee;
import com.aao.entity.User;
import com.aao.repo.EmployeeRepository;
import com.aao.repo.UserRepo;
import com.aao.response.ApiResponse;
import com.aao.serviceInterface.EmployeeService;
import com.aao.utils.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepo userRepo;
    private final EmployeeMapper employeeMapper;

    @Override
    @Transactional
    public ApiResponse<EmployeeDto> createEmployee(EmployeeRequestDto employeeRequestDto) {

        // Validate user ID
        if (employeeRequestDto.getUserId() == null) {
            return new ApiResponse<>(400, "User ID cannot be null", null);
        }

        // Verify User exists
        User user = userRepo.findById(employeeRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "User not found with ID: " + employeeRequestDto.getUserId()));

        // Check if user is already an employee
        if (employeeRepository.existsByUser_Id(user.getId())) {
            return new ApiResponse<>(400, "User is already an employee", null);
        }

        // Validate employee code uniqueness
        if (employeeRequestDto.getEmployeeCode() != null
                && employeeRepository.existsByEmployeeCode(employeeRequestDto.getEmployeeCode())) {
            return new ApiResponse<>(400, "Employee code already exists", null);
        }

        // Create new employee
        Employee employee = new Employee();
        employee.setUser(user);
        employee.setEmployeeRole(employeeRequestDto.getEmployeeRole());
        employee.setEmployeeCode(employeeRequestDto.getEmployeeCode());
        employee.setHireDate(employeeRequestDto.getHireDate() != null
                ? employeeRequestDto.getHireDate()
                : LocalDate.now());
        employee.setSalary(employeeRequestDto.getSalary());
        employee.setDepartment(employeeRequestDto.getDepartment());

        // Set manager if provided
        if (employeeRequestDto.getManagerId() != null) {
            Employee manager = employeeRepository.findById(employeeRequestDto.getManagerId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Manager not found with ID: " + employeeRequestDto.getManagerId()));
            employee.setManager(manager);
        }

        employee.setIsActive(true);

        Employee savedEmployee = employeeRepository.save(employee);
        return new ApiResponse<>(201, "Employee created successfully",
                employeeMapper.toDto(savedEmployee));
    }

    @Override
    public ApiResponse<EmployeeDto> getEmployeeById(Long employeeId) {
        if (employeeId == null) {
            return new ApiResponse<>(400, "Employee ID cannot be null", null);
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Employee not found with ID: " + employeeId));

        return new ApiResponse<>(200, "Employee details retrieved successfully",
                employeeMapper.toDto(employee));
    }

    @Override
    public ApiResponse<List<EmployeeDto>> getAllEmployees(String department, String role, Boolean isActive) {
        List<Employee> employees;

        // Apply filters based on provided parameters
        if (department != null && isActive != null) {
            employees = employeeRepository.findByDepartmentAndIsActive(department, isActive);
        } else if (role != null && isActive != null) {
            employees = employeeRepository.findByEmployeeRoleAndIsActive(role, isActive);
        } else if (department != null) {
            employees = employeeRepository.findByDepartment(department);
        } else if (role != null) {
            employees = employeeRepository.findByEmployeeRole(role);
        } else if (isActive != null) {
            employees = employeeRepository.findByIsActive(isActive);
        } else {
            employees = employeeRepository.findAll();
        }

        List<EmployeeDto> employeeDtos = employees.stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());

        return new ApiResponse<>(200, "Employees retrieved successfully", employeeDtos);
    }

    @Override
    @Transactional
    public ApiResponse<EmployeeDto> updateEmployee(Long employeeId, EmployeeRequestDto employeeRequestDto) {
        if (employeeId == null) {
            return new ApiResponse<>(400, "Employee ID cannot be null", null);
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Employee not found with ID: " + employeeId));

        // Update fields if provided
        if (employeeRequestDto.getEmployeeRole() != null) {
            employee.setEmployeeRole(employeeRequestDto.getEmployeeRole());
        }

        if (employeeRequestDto.getEmployeeCode() != null
                && !employeeRequestDto.getEmployeeCode().equals(employee.getEmployeeCode())) {
            // Check if new employee code is unique
            if (employeeRepository.existsByEmployeeCode(employeeRequestDto.getEmployeeCode())) {
                return new ApiResponse<>(400, "Employee code already exists", null);
            }
            employee.setEmployeeCode(employeeRequestDto.getEmployeeCode());
        }

        if (employeeRequestDto.getSalary() != null) {
            employee.setSalary(employeeRequestDto.getSalary());
        }

        if (employeeRequestDto.getDepartment() != null) {
            employee.setDepartment(employeeRequestDto.getDepartment());
        }

        if (employeeRequestDto.getHireDate() != null) {
            employee.setHireDate(employeeRequestDto.getHireDate());
        }

        // Update manager if provided
        if (employeeRequestDto.getManagerId() != null) {
            Employee manager = employeeRepository.findById(employeeRequestDto.getManagerId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Manager not found with ID: " + employeeRequestDto.getManagerId()));
            employee.setManager(manager);
        }

        Employee savedEmployee = employeeRepository.save(employee);
        return new ApiResponse<>(200, "Employee updated successfully",
                employeeMapper.toDto(savedEmployee));
    }

    @Override
    @Transactional
    public ApiResponse<Void> deleteEmployee(Long employeeId) {
        if (employeeId == null) {
            return new ApiResponse<>(400, "Employee ID cannot be null", null);
        }

        if (!employeeRepository.existsById(employeeId)) {
            throw new IllegalArgumentException("Employee not found with ID: " + employeeId);
        }

        employeeRepository.deleteById(employeeId);
        return new ApiResponse<>(200, "Employee deleted successfully", null);
    }

    @Override
    public ApiResponse<Void> recordAttendance(AttendanceDto attendanceDto) {
        if (attendanceDto.getEmployeeId() == null) {
            return new ApiResponse<>(400, "Employee ID cannot be null", null);
        }

        // Verify employee exists
        Employee employee = employeeRepository.findById(attendanceDto.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Employee not found with ID: " + attendanceDto.getEmployeeId()));

        // In a real implementation, this would save to an Attendance table
        // For now, we just log it and return success
        LocalDateTime checkIn = attendanceDto.getCheckInTime() != null
                ? attendanceDto.getCheckInTime()
                : LocalDateTime.now();

        String message = String.format("Attendance recorded for employee %s (%s) - Check-in: %s",
                employee.getUser().getFullName(),
                employee.getEmployeeCode(),
                checkIn);

        return new ApiResponse<>(200, message, null);
    }

    @Override
    @Transactional
    public ApiResponse<Void> activateEmployee(Long employeeId) {
        if (employeeId == null) {
            return new ApiResponse<>(400, "Employee ID cannot be null", null);
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Employee not found with ID: " + employeeId));

        employee.setIsActive(true);
        employeeRepository.save(employee);

        return new ApiResponse<>(200, "Employee status updated successfully", null);
    }

    @Override
    @Transactional
    public ApiResponse<Void> deactivateEmployee(Long employeeId) {
        if (employeeId == null) {
            return new ApiResponse<>(400, "Employee ID cannot be null", null);
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Employee not found with ID: " + employeeId));

        employee.setIsActive(false);
        employeeRepository.save(employee);

        return new ApiResponse<>(200, "Employee status updated successfully", null);
    }

    @Override
    @Transactional
    public ApiResponse<EmployeeDto> transferEmployee(Long employeeId, EmployeeTransferDto transferDto) {
        if (employeeId == null) {
            return new ApiResponse<>(400, "Employee ID cannot be null", null);
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Employee not found with ID: " + employeeId));

        // Update department if provided
        if (transferDto.getNewDepartment() != null) {
            employee.setDepartment(transferDto.getNewDepartment());
        }

        // Update role if provided
        if (transferDto.getNewRole() != null) {
            employee.setEmployeeRole(transferDto.getNewRole());
        }

        // Update manager if provided
        if (transferDto.getNewManagerId() != null) {
            Employee newManager = employeeRepository.findById(transferDto.getNewManagerId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Manager not found with ID: " + transferDto.getNewManagerId()));
            employee.setManager(newManager);
        }

        Employee savedEmployee = employeeRepository.save(employee);
        return new ApiResponse<>(200, "Employee transfer completed successfully",
                employeeMapper.toDto(savedEmployee));
    }
}
