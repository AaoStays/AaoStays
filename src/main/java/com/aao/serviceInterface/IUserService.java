package com.aao.serviceInterface;



import com.aao.dto.AuthResponse;
import com.aao.dto.LoginRequest;
import com.aao.dto.RegisterRequest;

import com.aao.response.ApiResponse;




public interface IUserService  {
	
	ApiResponse<AuthResponse> register(RegisterRequest request);
	
	
	ApiResponse<AuthResponse> login(LoginRequest request);


	
	
}
