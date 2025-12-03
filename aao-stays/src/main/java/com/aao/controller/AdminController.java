package com.aao.controller;

import com.aao.dto.AdminDto;
import com.aao.dto.CreateAdminRequest;
import com.aao.dto.ResetPasswordRequest;
import com.aao.dto.UpdateAdminRequest;
import com.aao.entity.UserType;
import com.aao.response.ApiResponse;
import com.aao.serviceInterface.AdminService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<ApiResponse<AdminDto>> createAdmin(@Valid @RequestBody CreateAdminRequest request) {
        ApiResponse<AdminDto> response = adminService.createAdmin(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<ApiResponse<AdminDto>> getAdminById(@PathVariable Long adminId) {
        ApiResponse<AdminDto> response = adminService.getAdminById(adminId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AdminDto>>> getAllAdmins() {
        ApiResponse<List<AdminDto>> response = adminService.getAllAdmins();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{adminId}")
    public ResponseEntity<ApiResponse<String>> updateAdmin(
            @PathVariable Long adminId,
            @RequestBody UpdateAdminRequest request) {

        ApiResponse<String> response = adminService.updateAdmin(adminId, request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<ApiResponse<String>> deleteAdmin(@PathVariable Long adminId) {
        ApiResponse<String> response = adminService.deleteAdmin(adminId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{adminId}/audit-logs")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getAdminAuditLogs(@PathVariable Long adminId) {
        ApiResponse<List<Map<String, Object>>> response = adminService.getAdminAuditLogs(adminId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PatchMapping("/{adminId}/permissions")
    public ResponseEntity<ApiResponse<String>> updateAdminPermissions(
            @PathVariable Long adminId,
            @RequestBody Map<String, List<String>> permissionsMap) {

        List<String> permissions = permissionsMap.get("permissions");

        ApiResponse<String> response = adminService.updateAdminPermissions(adminId, permissions);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/{adminId}/reset-password")
    public ResponseEntity<ApiResponse<String>> resetAdminPassword(
            @PathVariable Long adminId,
            @Valid @RequestBody ResetPasswordRequest request) {

        ApiResponse<String> response = adminService.resetAdminPassword(adminId, request.getNewPassword());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PatchMapping("/{adminId}/activate")
    public ResponseEntity<ApiResponse<String>> activateAdmin(@PathVariable Long adminId) {
        ApiResponse<String> response = adminService.activateAdmin(adminId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PatchMapping("/{adminId}/deactivate")
    public ResponseEntity<ApiResponse<String>> deactivateAdmin(@PathVariable Long adminId) {
        ApiResponse<String> response = adminService.deactivateAdmin(adminId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PutMapping("/users/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> changeUserRole(
            @PathVariable Long userId,
            @RequestParam("role") UserType newUserType
    ) {
        ApiResponse<String> response = adminService.changeUserRole(userId, newUserType);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
