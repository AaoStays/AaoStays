package com.aao.serviceImpl;

import com.aao.dto.HostDto;
import com.aao.entity.Host;
import com.aao.entity.User;
import com.aao.entity.UserType;
import com.aao.repo.HostRepo;
import com.aao.repo.UserRepo;
import com.aao.response.ApiResponse;
import com.aao.serviceInterface.HostService;
import com.aao.utils.HostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HostServiceImpl implements HostService {

    private final HostRepo hostRepo;
    private final UserRepo userRepo;
    private final HostMapper hostMapper;
    @Override
    @Transactional
    public ApiResponse<HostDto> createHost(HostDto hostDto) {

        Long userId = hostDto.getUserId();
        if (userId == null) {
            return new ApiResponse<>(400, "User ID cannot be null", null);
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        
        if (hostRepo.existsByUser_Id(user.getId())) {
            return new ApiResponse<>(400, "User is already a host", null);
        }

       
        Host host = new Host();
        host.setUser(user);

        host.setBio(null);
        host.setProfilePicture(null);
        host.setLanguagesSpoken(null);
        host.setGovernmentIdType(null);
        host.setGovernmentIdNumber(null);

     
        host.setHostSince(LocalDate.now());
        host.setIsSuperhost(false);
        host.setIsVerified(false);
        host.setIdVerified(false);
        host.setVerificationStatus("PENDING");
        host.setStatus("ACTIVE");

       
        host.setTotalProperties(0);
        host.setActiveProperties(0);
        host.setTotalBookings(0);
        host.setTotalEarnings(BigDecimal.ZERO);
        host.setEarningsPerMonth(BigDecimal.ZERO);
        host.setAverageRating(BigDecimal.ZERO);
        host.setResponseRate(BigDecimal.ZERO);
        host.setResponseTime(0);
        
        
        host.setIsProfileCompleted(false);

        // If user provided updated address
        if (hostDto.getAddress() != null) {
            user.setAddress(hostDto.getAddress());
            userRepo.save(user);
        }

        Host savedHost = hostRepo.save(host);
        return new ApiResponse<>(201, "Host account created successfully", hostMapper.toDto(savedHost));
    }

    @Override
    public ApiResponse<HostDto> getHostById(Long hostId) {
        if (hostId == null) {
            return new ApiResponse<>(400, "Host ID cannot be null", null);
        }
        Host host = hostRepo.findById(hostId)
                .orElseThrow(() -> new IllegalArgumentException("Host not found with ID: " + hostId));
        return new ApiResponse<>(200, "Host details retrieved successfully", hostMapper.toDto(host));
    }

    @Override
    public ApiResponse<List<HostDto>> getAllHosts() {
        List<HostDto> hosts = hostRepo.findAll().stream()
                .map(hostMapper::toDto)
                .collect(Collectors.toList());
        return new ApiResponse<>(200, "Hosts retrieved successfully", hosts);
    }

    @Override
    @Transactional
    public ApiResponse<HostDto> updateHost(Long hostId, HostDto hostDto) {
        if (hostId == null) {
            return new ApiResponse<>(400, "Host ID cannot be null", null);
        }
        Host host = hostRepo.findById(hostId)
                .orElseThrow(() -> new IllegalArgumentException("Host not found with ID: " + hostId));

        if (hostDto.getCompanyName() != null)
            host.setCompanyName(hostDto.getCompanyName());
        if (hostDto.getGstNumber() != null)
            host.setGstNumber(hostDto.getGstNumber());
        if (hostDto.getBio() != null)
            host.setBio(hostDto.getBio());
        if (hostDto.getProfilePicture() != null)
            host.setProfilePicture(hostDto.getProfilePicture());
        if (hostDto.getLanguagesSpoken() != null)
            host.setLanguagesSpoken(hostDto.getLanguagesSpoken());

        if (hostDto.getAddress() != null) {
            User user = host.getUser();
            user.setAddress(hostDto.getAddress());
            userRepo.save(user);
        }

        Host savedHost = hostRepo.save(host);
        return new ApiResponse<>(200, "Host updated successfully", hostMapper.toDto(savedHost));
    }

    @Override
    public ApiResponse<Void> deleteHost(Long hostId) {
        if (hostId == null) {
            return new ApiResponse<>(400, "Host ID cannot be null", null);
        }
        if (!hostRepo.existsById(hostId)) {
            throw new IllegalArgumentException("Host not found with ID: " + hostId);
        }
        hostRepo.deleteById(hostId);
        return new ApiResponse<>(200, "Operation successful", null);
    }

    @Override
    @Transactional
    public ApiResponse<HostDto> verifyHostIdentity(Long hostId) {
        if (hostId == null) {
            return new ApiResponse<>(400, "Host ID cannot be null", null);
        }
        Host host = hostRepo.findById(hostId)
                .orElseThrow(() -> new IllegalArgumentException("Host not found with ID: " + hostId));

        host.setIsVerified(true);
        host.setVerificationStatus("VERIFIED");
        host.setIdVerified(true);

        Host savedHost = hostRepo.save(host);
        return new ApiResponse<>(200, "Host identity verified successfully", hostMapper.toDto(savedHost));
    }

    @Override
    @Transactional
    public ApiResponse<Void> activateHost(Long hostId) {
        if (hostId == null) {
            return new ApiResponse<>(400, "Host ID cannot be null", null);
        }
        Host host = hostRepo.findById(hostId)
                .orElseThrow(() -> new IllegalArgumentException("Host not found with ID: " + hostId));
        host.setStatus("ACTIVE");
        hostRepo.save(host);
        return new ApiResponse<>(200, "Host status updated successfully", null);
    }

    @Override
    @Transactional
    public ApiResponse<Void> deactivateHost(Long hostId) {
        if (hostId == null) {
            return new ApiResponse<>(400, "Host ID cannot be null", null);
        }
        Host host = hostRepo.findById(hostId)
                .orElseThrow(() -> new IllegalArgumentException("Host not found with ID: " + hostId));
        host.setStatus("INACTIVE");
        hostRepo.save(host);
        return new ApiResponse<>(200, "Host status updated successfully", null);
    }
    
    @Override
    @Transactional
    public ApiResponse<HostDto> completeHostProfile(HostDto hostDto) {

        if (hostDto.getUserId() == null) {
            return new ApiResponse<>(400, "User ID cannot be null", null);
        }

        Host host = hostRepo.findByUser_Id(hostDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Host not found for user ID: " + hostDto.getUserId()));

        if (host.getIsProfileCompleted()) {
            return new ApiResponse<>(400, "Host profile is already completed", null);
        }

        // Only host-editable fields
        host.setBio(hostDto.getBio());
        host.setProfilePicture(hostDto.getProfilePicture());
        host.setLanguagesSpoken(hostDto.getLanguagesSpoken());
        host.setGovernmentIdType(hostDto.getGovernmentIdType());
        host.setGovernmentIdNumber(hostDto.getGovernmentIdNumber());

        host.setIsProfileCompleted(true);
        host.setVerificationStatus("UNDER_REVIEW"); // optional
        host.setIdVerified(false);                   // must be verified by admin

        Host saved = hostRepo.save(host);

        return new ApiResponse<>(200, "Host profile completed successfully", hostMapper.toDto(saved));
    }

  


}
