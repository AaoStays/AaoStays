package com.aao.serviceImpl;

import com.aao.dto.HostOnboardingRequestDto;
import com.aao.dto.HostResponseDto;
import com.aao.entity.Host;
import com.aao.entity.User;
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

	// ✔ CREATE HOST (Admin assigns host role)
	@Override
	@Transactional
	public ApiResponse<HostResponseDto> createHost(HostOnboardingRequestDto hostDto) {

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

		// defaults
		host.setBio(null);
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

		Host savedHost = hostRepo.save(host);

		return new ApiResponse<>(201, "Host account created successfully", hostMapper.toResponseDto(savedHost));
	}

	// ✔ GET HOST BY ID
	@Override
	public ApiResponse<HostResponseDto> getHostById(Long hostId) {
		if (hostId == null) {
			return new ApiResponse<>(400, "Host ID cannot be null", null);
		}

		Host host = hostRepo.findById(hostId)
				.orElseThrow(() -> new IllegalArgumentException("Host not found with ID: " + hostId));

		return new ApiResponse<>(200, "Host details retrieved successfully", hostMapper.toResponseDto(host));
	}

	@Override
	public ApiResponse<List<HostResponseDto>> getAllHosts() {

		List<HostResponseDto> hosts = hostRepo.findAll().stream().map(hostMapper::toResponseDto)
				.collect(Collectors.toList());

		return new ApiResponse<>(200, "Hosts retrieved successfully", hosts);
	}

	@Override
	@Transactional
	public ApiResponse<HostResponseDto> updateHost(Long hostId, HostOnboardingRequestDto hostDto) {

		if (hostId == null) {
			return new ApiResponse<>(400, "Host ID cannot be null", null);
		}

		Host host = hostRepo.findById(hostId)
				.orElseThrow(() -> new IllegalArgumentException("Host not found with ID: " + hostId));

		if (hostDto.getBio() != null)
			host.setBio(hostDto.getBio());
		if (hostDto.getLanguagesSpoken() != null)
			host.setLanguagesSpoken(hostDto.getLanguagesSpoken());
		if (hostDto.getGovernmentIdType() != null)
			host.setGovernmentIdType(hostDto.getGovernmentIdType());
		if (hostDto.getGovernmentIdNumber() != null)
			host.setGovernmentIdNumber(hostDto.getGovernmentIdNumber());

		Host savedHost = hostRepo.save(host);

		return new ApiResponse<>(200, "Host updated successfully", hostMapper.toResponseDto(savedHost));
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

	// ✔ VERIFY HOST IDENTITY
	@Override
	@Transactional
	public ApiResponse<HostResponseDto> verifyHostIdentity(Long hostId) {

		if (hostId == null) {
			return new ApiResponse<>(400, "Host ID cannot be null", null);
		}

		Host host = hostRepo.findById(hostId)
				.orElseThrow(() -> new IllegalArgumentException("Host not found with ID: " + hostId));

		host.setIsVerified(true);
		host.setVerificationStatus("VERIFIED");
		host.setIdVerified(true);

		Host savedHost = hostRepo.save(host);

		return new ApiResponse<>(200, "Host identity verified successfully", hostMapper.toResponseDto(savedHost));
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

		return new ApiResponse<>(200, "Host activated successfully", null);
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

		return new ApiResponse<>(200, "Host deactivated successfully", null);
	}

	@Override
	public ApiResponse<HostResponseDto> completeHostProfile(HostOnboardingRequestDto hostDto) {

	    if (hostDto.getUserId() == null) {
	        return new ApiResponse<>(400, "User ID cannot be null", null);
	    }

	    Host host = hostRepo.findByUser_Id(hostDto.getUserId())
	            .orElseThrow(() -> new IllegalArgumentException(
	                    "Host not found for user ID: " + hostDto.getUserId()));

	    if (host.getIsProfileCompleted()) {
	        return new ApiResponse<>(400, "Host profile is already completed", null);
	    }

	    if (hostDto.getBio() != null) host.setBio(hostDto.getBio());
	    if (hostDto.getLanguagesSpoken() != null) host.setLanguagesSpoken(hostDto.getLanguagesSpoken());
	    if (hostDto.getGovernmentIdType() != null) host.setGovernmentIdType(hostDto.getGovernmentIdType());
	    if (hostDto.getGovernmentIdNumber() != null) host.setGovernmentIdNumber(hostDto.getGovernmentIdNumber());

	    host.setIsProfileCompleted(true);
	    host.setVerificationStatus("UNDER_REVIEW");
	    host.setIdVerified(false);

	    Host saved = hostRepo.save(host);

	    return new ApiResponse<>(200, "Host profile completed successfully",
	            hostMapper.toResponseDto(saved));
		
	}

}
