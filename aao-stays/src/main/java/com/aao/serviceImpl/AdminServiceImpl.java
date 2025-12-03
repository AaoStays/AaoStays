package com.aao.serviceImpl;

import com.aao.dto.AdminDto;
import com.aao.dto.CreateAdminRequest;
import com.aao.dto.UpdateAdminRequest;
import com.aao.entity.Admin;
import com.aao.entity.User;
import com.aao.entity.UserType;
import com.aao.utils.AdminMapper;
import com.aao.repo.AdminRepo;
import com.aao.repo.UserRepo;
import com.aao.response.ApiResponse;
import com.aao.serviceInterface.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepo adminRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AdminMapper adminMapper;

    @Override
    @Transactional
    public ApiResponse<AdminDto> createAdmin(CreateAdminRequest request) {
        if (request.getUserId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + request.getUserId()));

        if (adminRepo.existsByUser(user)) {
            throw new IllegalArgumentException("User is already an admin");
        }

        // Update User type to ADMIN
        user.setUserType(UserType.ADMIN);
        userRepo.save(user);

        Admin admin = Admin.builder()
                .user(user)
                .adminLevel(request.getRole())
                .permissions(request.getPermissions())
                .department(request.getDepartment())
                .build();

        Admin savedAdmin = adminRepo.save(admin);

        return new ApiResponse<>(201, "Admin created successfully", adminMapper.toDto(savedAdmin));
    }

    @Override
    public ApiResponse<AdminDto> getAdminById(Long adminId) {
        Admin admin = adminRepo.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with ID: " + adminId));
        return new ApiResponse<>(200, "Admin details fetched successfully", adminMapper.toDto(admin));
    }

    @Override
    public ApiResponse<List<AdminDto>> getAllAdmins() {
        List<Admin> admins = adminRepo.findAll();
        List<AdminDto> adminDtos = admins.stream()
                .map(adminMapper::toDto)
                .collect(Collectors.toList());
        return new ApiResponse<>(200, "Admins fetched successfully", adminDtos);
    }

    @Override
    @Transactional
    public ApiResponse<String> updateAdmin(Long adminId, UpdateAdminRequest request) {
        Admin admin = adminRepo.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with ID: " + adminId));

        if (request.getRole() != null) {
            admin.setAdminLevel(request.getRole());
        }
        if (request.getPermissions() != null) {
            admin.setPermissions(request.getPermissions());
        }
        if (request.getDepartment() != null) {
            admin.setDepartment(request.getDepartment());
        }

        adminRepo.save(admin);
        return new ApiResponse<>(200, "Admin updated successfully", null);
    }

    @Override
    @Transactional
    public ApiResponse<String> deleteAdmin(Long adminId) {
        Admin admin = adminRepo.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with ID: " + adminId));

        User user = admin.getUser();
        user.setUserType(UserType.GUEST); // Reverting to GUEST
        userRepo.save(user);

        adminRepo.delete(admin);
        return new ApiResponse<>(200, "Admin deleted successfully", null);
    }

    @Override
    public ApiResponse<List<Map<String, Object>>> getAdminAuditLogs(Long adminId) {
        // Mock implementation
        List<Map<String, Object>> logs = new ArrayList<>();
        Map<String, Object> log1 = new HashMap<>();
        log1.put("timestamp", LocalDateTime.now().minusHours(2));
        log1.put("action", "LOGIN");
        log1.put("ip_address", "192.168.1.1");
        logs.add(log1);

        return new ApiResponse<>(200, "Audit logs fetched successfully", logs);
    }

    @Override
    @Transactional
    public ApiResponse<String> updateAdminPermissions(Long adminId, List<String> permissions) {
        Admin admin = adminRepo.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with ID: " + adminId));

        admin.setPermissions(permissions);
        adminRepo.save(admin);
        return new ApiResponse<>(200, "Admin permissions updated successfully", null);
    }

    @Override
    @Transactional
    public ApiResponse<String> resetAdminPassword(Long adminId, String newPassword) {
        Admin admin = adminRepo.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with ID: " + adminId));

        User user = admin.getUser();
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        return new ApiResponse<>(200, "Admin password reset successfully", null);
    }

    @Override
    @Transactional
    public ApiResponse<String> activateAdmin(Long adminId) {
        Admin admin = adminRepo.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with ID: " + adminId));

        User user = admin.getUser();
        user.setIsActive(true);
        userRepo.save(user);

        return new ApiResponse<>(200, "Admin activated successfully", null);
    }

    @Override
    @Transactional
    public ApiResponse<String> deactivateAdmin(Long adminId) {
        Admin admin = adminRepo.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with ID: " + adminId));

        User user = admin.getUser();
        user.setIsActive(false);
        userRepo.save(user);

        return new ApiResponse<>(200, "Admin deactivated successfully", null);
    }
    
    @Override
    public ApiResponse<String> changeUserRole(Long userId, UserType newUserType) {

        
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

       
        if (user.getUserType() == newUserType) {
            throw new IllegalArgumentException("User is already a " + newUserType);
        }

        user.setUserType(newUserType);
        userRepo.save(user);

    
        return new ApiResponse<>(
                200,
                "User role updated successfully to " + newUserType,
                null
        );
    }

}
