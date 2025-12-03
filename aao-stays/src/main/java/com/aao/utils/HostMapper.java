package com.aao.utils;

import com.aao.dto.HostDto;
import com.aao.entity.Host;
import com.aao.entity.User;
import com.aao.entity.VerificationStatus;
import org.springframework.stereotype.Component;

@Component
public class HostMapper {

    public HostDto toDto(Host host) {
        if (host == null) {
            return null;
        }
        User user = host.getUser();
        return HostDto.builder()
                .hostId(host.getHostId())
                .userId(user != null ? user.getId() : null)
                .hostFullName(user != null ? user.getFullName() : null)
                .email(user != null ? user.getEmail() : null)
                .phoneNumber(user != null ? user.getPhoneNumber() : null)
                .address(user != null ? user.getAddress() : null)
                .companyName(host.getCompanyName())
                .gstNumber(host.getGstNumber())
                .bio(host.getBio())
                .profilePicture(host.getProfilePicture())
                .languagesSpoken(host.getLanguagesSpoken())
                .isSuperhost(host.getIsSuperhost())
                .hostSince(host.getHostSince())
                .isVerified(host.getIsVerified())
                .verificationStatus(
                        host.getVerificationStatus() != null ? VerificationStatus.valueOf(host.getVerificationStatus())
                                : null)
                .governmentIdType(host.getGovernmentIdType())
                .governmentIdNumber(host.getGovernmentIdNumber())
                .idVerified(host.getIdVerified())
                .totalProperties(host.getTotalProperties())
                .activeProperties(host.getActiveProperties())
                .totalBookings(host.getTotalBookings())
                .totalEarnings(host.getTotalEarnings())
                .earningsPerMonth(host.getEarningsPerMonth())
                .averageRating(host.getAverageRating())
                .responseRate(host.getResponseRate())
                .responseTime(host.getResponseTime())
                .status(host.getStatus())
                .createdAt(host.getCreatedAt())
                .updatedAt(host.getUpdatedAt())
                .isProfileCompleted(host.getIsProfileCompleted())
                .build();
    }
}
