package com.aao.utils;

import com.aao.dto.EmployeeDto;
import com.aao.entity.Employee;
import com.aao.entity.User;
import org.springframework.stereotype.Component;

/**
 * Mapper utility for converting between Employee entity and EmployeeDto.
 */
@Component
public class EmployeeMapper {

    /**
     * Converts Employee entity to EmployeeDto.
     * 
     * @param employee Employee entity
     * @return EmployeeDto
     */
    public EmployeeDto toDto(Employee employee) {
        if (employee == null) {
            return null;
        }

        User user = employee.getUser();
        Employee manager = employee.getManager();

        return EmployeeDto.builder()
                .employeeId(employee.getEmployeeId())
                .userId(user != null ? user.getId() : null)
                .fullName(user != null ? user.getFullName() : null)
                .email(user != null ? user.getEmail() : null)
                .phoneNumber(user != null ? user.getPhoneNumber() : null)
                .address(user != null ? user.getAddress() : null)
                .employeeRole(employee.getEmployeeRole())
                .employeeCode(employee.getEmployeeCode())
                .hireDate(employee.getHireDate())
                .salary(employee.getSalary())
                .department(employee.getDepartment())
                .managerId(manager != null ? manager.getEmployeeId() : null)
                .managerName(manager != null && manager.getUser() != null
                        ? manager.getUser().getFullName()
                        : null)
                .isActive(employee.getIsActive())
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }
}
