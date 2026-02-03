package com.aao.serviceInterface;

import com.aao.dto.ContactDetailsRequestDto;
import com.aao.dto.ContactDetailsResponseDto;
import com.aao.response.ApiResponse;

public class ContactDetailServiceImpl implements ContactDetailsService {

	@Override
	public ApiResponse<ContactDetailsResponseDto> addContactDetails(ContactDetailsRequestDto contactDetailsRequestDto) {
		
		return null;
	}

	@Override
	public ApiResponse<ContactDetailsResponseDto> getContactDetails(Long id) {
		
		return null;
	}

	@Override
	public ApiResponse<Void> deleteContactDetails(Long id) {

		return null;
	}

	@Override
	public ApiResponse<ContactDetailsResponseDto> updateContactDetails(
			ContactDetailsRequestDto contactDetailsRequestDto) {
		
		return null;
	}

}
