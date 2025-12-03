package com.aao.serviceInterface;



import java.util.List;

import com.aao.dto.AuthResponse;
import com.aao.dto.LoginRequest;
import com.aao.dto.RegisterRequest;

import com.aao.dto.UserResponse;
import com.aao.response.ApiResponse;




public interface IUserService  {
	
	ApiResponse<AuthResponse> register(RegisterRequest request);
	
	
	ApiResponse<AuthResponse> login(LoginRequest request);
    
	ApiResponse<UserResponse> deleteUserById(Long id);
	
	ApiResponse<UserResponse>  getUserById(Long id);
	
	ApiResponse<List <UserResponse>> getAllUsers();
	
	ApiResponse<String> updateUserRole(Long userId, String role);

	
	
 
	  
	
}
