package com.aao.dto;

import java.time.LocalDate;

import com.aao.entity.UserType;

import lombok.Data;
@Data
public class RegisterRequest {
	private String email;
	private String password;
	private String fullName;
	private String phoneNumber;
	private UserType userType;
	private String profilePictureUrl;
	private LocalDate dateOfBirth;
	private String address;
}
