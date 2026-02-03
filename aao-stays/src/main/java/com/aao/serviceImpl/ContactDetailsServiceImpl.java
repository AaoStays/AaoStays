package com.aao.serviceImpl;

import org.springframework.stereotype.Service;

import com.aao.dto.ContactDetailsRequestDto;
import com.aao.dto.ContactDetailsResponseDto;
import com.aao.entity.ContactDetails;
import com.aao.repo.ContactDetailsRepo;
import com.aao.response.ApiResponse;
import com.aao.serviceInterface.ContactDetailsService;
import com.aao.utils.ContactDetailsMapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactDetailsServiceImpl implements ContactDetailsService{
	
	private final ContactDetailsRepo contactDetailsRepo;
	
	private ContactDetailsMapper contactDetailsMapper;

	@Override
	public ApiResponse<ContactDetailsResponseDto> addContactDetails(ContactDetailsRequestDto contactDetailsRequestDto) {
		 
		 boolean exists = contactDetailsRepo.existsByprimaryMobileOrEmail(
			        contactDetailsRequestDto.getEmail(),
			        contactDetailsRequestDto.getPrimaryMobile()
			    );

			    if (exists) {
			        throw new IllegalArgumentException(
			            "Contact details with this email or phone number already exist"
			        );
			    }
			    
			 ContactDetails contactDetails= contactDetailsMapper.toEntity(contactDetailsRequestDto);
			 
			ContactDetails saved= contactDetailsRepo.save(contactDetails);
			
			
			

		 
		 
		return new ApiResponse<ContactDetailsResponseDto>(201, "contact Details saved successfully", contactDetailsMapper.toDto(contactDetails));
	}

	@Override
	public ApiResponse<ContactDetailsResponseDto> getContactDetails(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse<Void> deleteContactDetails(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse<ContactDetailsResponseDto> updateContactDetails(
			ContactDetailsRequestDto contactDetailsRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}
  
	
	
} 
