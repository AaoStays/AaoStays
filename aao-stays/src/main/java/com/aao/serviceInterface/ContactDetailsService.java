package com.aao.serviceInterface;

import com.aao.dto.ContactDetailsRequestDto;
import com.aao.dto.ContactDetailsResponseDto;
import com.aao.response.ApiResponse;

public interface ContactDetailsService {
   
	
	
	ApiResponse<ContactDetailsResponseDto> addContactDetails(ContactDetailsRequestDto contactDetailsRequestDto );
	
	ApiResponse<ContactDetailsResponseDto>  getContactDetails(Long id);
	
	ApiResponse<Void>  deleteContactDetails(Long id);
	
	ApiResponse<ContactDetailsResponseDto> updateContactDetails(ContactDetailsRequestDto contactDetailsRequestDto);
	
}
