package com.aao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aao.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Check if user is already an employee
    boolean existsByUser_Id(Long userId);

    // Find employee by user ID
    Optional<Employee> findByUser_Id(Long userId);

    // Check if employee code already exists
    boolean existsByEmployeeCode(String employeeCode);

    // Filter by department
    List<Employee> findByDepartment(String department);

    // Filter by employee role
    List<Employee> findByEmployeeRole(String employeeRole);

    // Filter by active status
    List<Employee> findByIsActive(Boolean isActive);

    // Find employees under a specific manager
    List<Employee> findByManager_EmployeeId(Long managerId);

    // Combined filters
    List<Employee> findByDepartmentAndIsActive(String department, Boolean isActive);

    List<Employee> findByEmployeeRoleAndIsActive(String employeeRole, Boolean isActive);
}
