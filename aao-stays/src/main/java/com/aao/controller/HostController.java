package com.aao.controller;

import com.aao.dto.HostOnboardingRequestDto;
import com.aao.dto.HostResponseDto;
import com.aao.response.ApiResponse;
import com.aao.security.CustomUserDetails;
import com.aao.serviceInterface.HostService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hosts")
@RequiredArgsConstructor
public class HostController {

    private final HostService hostService;

    @PostMapping
    public ResponseEntity<ApiResponse<HostResponseDto>> createHost(@RequestBody HostOnboardingRequestDto hostDto) {
        ApiResponse<HostResponseDto> response = hostService.createHost(hostDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{hostId}") 
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<HostResponseDto>> getHostById(@PathVariable Long hostId) {
        ApiResponse<HostResponseDto> response = hostService.getHostById(hostId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<HostResponseDto>>> getAllHosts() {
        ApiResponse<List<HostResponseDto>> response = hostService.getAllHosts();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{hostId}")
    public ResponseEntity<ApiResponse<HostResponseDto>> updateHost(@PathVariable Long hostId, @RequestBody HostOnboardingRequestDto hostDto) {
        ApiResponse<HostResponseDto> response = hostService.updateHost(hostId, hostDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{hostId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteHost(@PathVariable Long hostId) {
        ApiResponse<Void> response = hostService.deleteHost(hostId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/{hostId}/verify-identity")
    public ResponseEntity<ApiResponse<HostResponseDto>> verifyHostIdentity(@PathVariable Long hostId) {
        ApiResponse<HostResponseDto> response = hostService.verifyHostIdentity(hostId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    @PatchMapping("/activate/{hostId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> activateHost(@PathVariable Long hostId) {
        ApiResponse<Void> response = hostService.activateHost(hostId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PatchMapping("/deactivate/{hostId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deactivateHost(@PathVariable Long hostId) {
        ApiResponse<Void> response = hostService.deactivateHost(hostId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    } 
    @PostMapping("/completeProfile")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<ApiResponse<HostResponseDto>> completeProfile(
            @RequestBody HostOnboardingRequestDto hostDto,
            Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long currentUserId = userDetails.getUser().getId();

       
        hostDto.setUserId(currentUserId);

        ApiResponse<HostResponseDto> response = hostService.completeHostProfile(hostDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
