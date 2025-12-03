package com.aao.serviceInterface;

import com.aao.dto.HostDto;
import com.aao.response.ApiResponse;
import java.util.List;

public interface HostService {
    ApiResponse<HostDto> createHost(HostDto hostDto);

    ApiResponse<HostDto> getHostById(Long hostId);

    ApiResponse<List<HostDto>> getAllHosts();

    ApiResponse<HostDto> updateHost(Long hostId, HostDto hostDto);

    ApiResponse<Void> deleteHost(Long hostId);

    ApiResponse<HostDto> verifyHostIdentity(Long hostId);

    ApiResponse<Void> activateHost(Long hostId);

    ApiResponse<Void> deactivateHost(Long hostId);
    
    ApiResponse<HostDto> completeHostProfile(HostDto hostDto);
}
