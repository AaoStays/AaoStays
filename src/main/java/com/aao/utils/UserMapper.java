package com.aao.utils;

import com.aao.dto.RegisterRequest;
import com.aao.dto.UserDto;
import com.aao.entity.User;

public class UserMapper {
	
	public static UserDto mapUserEntityToUserDto(User user) {
        if (user == null) return null;

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setUserType(user.getUserType());
        dto.setProfilePictureUrl(user.getProfilePictureUrl());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setAddress(user.getAddress());
        dto.setIsActive(user.getIsActive());
        dto.setIsEmailVerified(user.getIsEmailVerified());
        dto.setIsPhoneVerified(user.getIsPhoneVerified());
        dto.setLastLogin(user.getLastLogin());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
	 public static User mapUserDtoToUserEntity(UserDto dto) {
	        if (dto == null) return null;

	        User user = new User();
	        user.setId(dto.getId());
	        user.setEmail(dto.getEmail());
	        user.setFullName(dto.getFullName());
	        user.setPhoneNumber(dto.getPhoneNumber());
	        user.setUserType(dto.getUserType());
	        user.setProfilePictureUrl(dto.getProfilePictureUrl());
	        user.setDateOfBirth(dto.getDateOfBirth());
	        user.setAddress(dto.getAddress());
	        user.setIsActive(dto.getIsActive());
	        user.setIsEmailVerified(dto.getIsEmailVerified());
	        user.setIsPhoneVerified(dto.getIsPhoneVerified());
	        user.setLastLogin(dto.getLastLogin());
	        return user;
	    }
	 public static User mapRegisterRequestToUserEntity(RegisterRequest request) {
	        if (request == null) return null;

	        return User.builder()
	                .email(request.getEmail())
	                .fullName(request.getFullName())
	                .phoneNumber(request.getPhoneNumber())
	                .userType(request.getUserType())
	                .profilePictureUrl(request.getProfilePictureUrl())
	                .dateOfBirth(request.getDateOfBirth())
	                .address(request.getAddress())
	                .build();
	    }
}
