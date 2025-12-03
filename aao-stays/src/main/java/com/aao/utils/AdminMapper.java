package com.aao.utils;

import com.aao.dto.AdminDto;
import com.aao.entity.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public AdminDto toDto(Admin admin) {
        if (admin == null) {
            return null;
        }
        return AdminDto.builder()
                .adminId(admin.getAdminId())
                .userId(admin.getUser() != null ? admin.getUser().getId() : null)
                .adminFullName(admin.getUser() != null ? admin.getUser().getFullName() : null)
                .email(admin.getUser() != null ? admin.getUser().getEmail() : null)
                .phoneNumber(admin.getUser() != null ? admin.getUser().getPhoneNumber() : null)
                .role(admin.getAdminLevel())
                .permissions(admin.getPermissions())
                .department(admin.getDepartment())
                .createdAt(admin.getCreatedAt())
                .build();
    }
}
