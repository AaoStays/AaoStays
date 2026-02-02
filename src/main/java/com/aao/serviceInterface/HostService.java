package com.aao.serviceInterface;

import com.aao.dto.HostOnboardingRequestDto;
import com.aao.dto.HostResponseDto;
import com.aao.response.ApiResponse;

import java.util.List;

public interface HostService {

    
    ApiResponse<HostResponseDto> createHost(HostOnboardingRequestDto hostDto);

    ApiResponse<HostResponseDto> getHostById(Long hostId);

    
    ApiResponse<List<HostResponseDto>> getAllHosts();

    
    ApiResponse<HostResponseDto> updateHost(Long hostId, HostOnboardingRequestDto hostDto);

    
    ApiResponse<Void> deleteHost(Long hostId);

    
    ApiResponse<HostResponseDto> verifyHostIdentity(Long hostId);

   
    ApiResponse<Void> activateHost(Long hostId);

    ApiResponse<Void> deactivateHost(Long hostId);

    ApiResponse<HostResponseDto> completeHostProfile(HostOnboardingRequestDto hostDto);
}
