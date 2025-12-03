package com.aao.dto;

import com.aao.entity.UserType;

import lombok.Data;

@Data
public class UserResponse {
 
	   private Long id;
	   private String fullName;
	    private String email;
	    private String phone;
	    private boolean emailVerified;
	    private boolean phoneVerified;
	    private UserType userType;
	    private String address;
}
