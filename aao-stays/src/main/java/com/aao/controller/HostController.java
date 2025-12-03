package com.aao.controller;

import com.aao.dto.HostDto;
import com.aao.response.ApiResponse;
import com.aao.serviceInterface.HostService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hosts")
@RequiredArgsConstructor
public class HostController {

    private final HostService hostService;

    @PostMapping
    public ResponseEntity<ApiResponse<HostDto>> createHost(@RequestBody HostDto hostDto) {
        ApiResponse<HostDto> response = hostService.createHost(hostDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{hostId}")
    public ResponseEntity<ApiResponse<HostDto>> getHostById(@PathVariable Long hostId) {
        ApiResponse<HostDto> response = hostService.getHostById(hostId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<HostDto>>> getAllHosts() {
        ApiResponse<List<HostDto>> response = hostService.getAllHosts();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{hostId}")
    public ResponseEntity<ApiResponse<HostDto>> updateHost(@PathVariable Long hostId, @RequestBody HostDto hostDto) {
        ApiResponse<HostDto> response = hostService.updateHost(hostId, hostDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{hostId}")
    public ResponseEntity<ApiResponse<Void>> deleteHost(@PathVariable Long hostId) {
        ApiResponse<Void> response = hostService.deleteHost(hostId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/{hostId}/verify-identity")
    public ResponseEntity<ApiResponse<HostDto>> verifyHostIdentity(@PathVariable Long hostId) {
        ApiResponse<HostDto> response = hostService.verifyHostIdentity(hostId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PatchMapping("/{hostId}/activate")
    public ResponseEntity<ApiResponse<Void>> activateHost(@PathVariable Long hostId) {
        ApiResponse<Void> response = hostService.activateHost(hostId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PatchMapping("/{hostId}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateHost(@PathVariable Long hostId) {
        ApiResponse<Void> response = hostService.deactivateHost(hostId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PostMapping("/complete-profile")
    public ResponseEntity<ApiResponse<HostDto>> completeProfile(@RequestBody HostDto hostDto) {
        ApiResponse<HostDto> response = hostService.completeHostProfile(hostDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
