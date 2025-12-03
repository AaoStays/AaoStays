package com.aao.serviceInterface;

import com.aao.dto.AdminDto;
import com.aao.dto.CreateAdminRequest;
import com.aao.dto.UpdateAdminRequest;
import com.aao.entity.UserType;
import com.aao.response.ApiResponse;

import java.util.List;
import java.util.Map;

public interface AdminService {
    ApiResponse<AdminDto> createAdmin(CreateAdminRequest request);

    ApiResponse<AdminDto> getAdminById(Long adminId);

    ApiResponse<List<AdminDto>> getAllAdmins();

    ApiResponse<String> updateAdmin(Long adminId, UpdateAdminRequest request);

    ApiResponse<String> deleteAdmin(Long adminId);

    
    ApiResponse<List<Map<String, Object>>> getAdminAuditLogs(Long adminId);

    ApiResponse<String> updateAdminPermissions(Long adminId, List<String> permissions);

    ApiResponse<String> resetAdminPassword(Long adminId, String newPassword);

    ApiResponse<String> activateAdmin(Long adminId);

    ApiResponse<String> deactivateAdmin(Long adminId);
    
    ApiResponse<String> changeUserRole(Long userId, UserType newUserType); 
}
