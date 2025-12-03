package com.aao.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aao.dto.UserResponse;
import com.aao.entity.User;
import com.aao.response.ApiResponse;
import com.aao.serviceInterface.IUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	
	public final IUserService userService;
	
	
	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> delteUserById(@PathVariable Long id ){
		ApiResponse<UserResponse> response=userService.deleteUserById(id);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@GetMapping("/getById/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long  id){
		
		ApiResponse<UserResponse> response=userService.getUserById(id);
		 return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@GetMapping("/getAll")
	public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(){
		ApiResponse<List<UserResponse>> response=userService.getAllUsers();
		
		return ResponseEntity.status(response.getStatusCode()).body(response);
		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@PutMapping("/update-role/{id}")
	public ResponseEntity<ApiResponse<String>> updateRole(
	        @PathVariable Long id,
	        @RequestParam String role
	) {
	    ApiResponse<String> response = userService.updateUserRole(id, role);
	    return ResponseEntity.status(response.getStatusCode()).body(response);
	}

}
