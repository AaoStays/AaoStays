package com.aao.utils;

import com.aao.dto.HostOnboardingRequestDto;
import com.aao.dto.HostResponseDto;
import com.aao.entity.Host;
import com.aao.entity.User;
import org.springframework.stereotype.Component;

@Component
public class HostMapper {

    // Host → HostResponseDto
    public HostResponseDto toResponseDto(Host host) {
        if (host == null) return null;

        User user = host.getUser();

        return HostResponseDto.builder()
                .hostId(host.getHostId())
                .userId(user != null ? user.getId() : null)

                .fullName(user != null ? user.getFullName() : null)
                .email(user != null ? user.getEmail() : null)
                .phoneNumber(user != null ? user.getPhoneNumber() : null)
                .address(user != null ? user.getAddress() : null)
        

                .bio(host.getBio())
                .languagesSpoken(host.getLanguagesSpoken())
                .governmentIdType(host.getGovernmentIdType())
                .governmentIdNumber(host.getGovernmentIdNumber())

                .isProfileCompleted(host.getIsProfileCompleted())
                .totalProperties(host.getTotalProperties())

                .build();
    }

   
    public void updateHostFromOnboardingDto(Host host, HostOnboardingRequestDto dto) {
        if (dto.getBio() != null) host.setBio(dto.getBio());
        if (dto.getLanguagesSpoken() != null) host.setLanguagesSpoken(dto.getLanguagesSpoken());
        if (dto.getGovernmentIdType() != null) host.setGovernmentIdType(dto.getGovernmentIdType());
        if (dto.getGovernmentIdNumber() != null) host.setGovernmentIdNumber(dto.getGovernmentIdNumber());

        host.setIsProfileCompleted(true);
    }
}




