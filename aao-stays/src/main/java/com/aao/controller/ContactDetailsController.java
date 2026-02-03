package com.aao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aao.dto.ContactDetailsRequestDto;
import com.aao.dto.ContactDetailsResponseDto;
import com.aao.response.ApiResponse;
import com.aao.serviceInterface.ContactDetailsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/contactDetails")
@RequiredArgsConstructor
public class ContactDetailsController {
   
	private final ContactDetailsService contactDetailsService;
	
	@PostMapping("addContact")
	@PreAuthorize("hasAnyRole('ADMIN','HOST')")
	public ResponseEntity<ApiResponse<ContactDetailsResponseDto>> addContact(@RequestBody   ContactDetailsRequestDto contactDetailsRequestDto){
 		
    ApiResponse<ContactDetailsResponseDto> response= contactDetailsService.addContactDetails(contactDetailsRequestDto);
   
    return ResponseEntity.status(response.getStatusCode()).body(response);

	}
	
}
