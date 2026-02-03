package com.aao.utils;

import org.springframework.stereotype.Component;

import com.aao.dto.ContactDetailsRequestDto;
import com.aao.dto.ContactDetailsResponseDto;
import com.aao.entity.ContactDetails;

@Component
public class ContactDetailsMapper {

	 public  ContactDetailsResponseDto toDto(ContactDetails contactDetails) {
		 
		  if(contactDetails==null) {
			  return null;
		  }
		  
		  ContactDetailsResponseDto contactDetailsResponseDto= new ContactDetailsResponseDto() ;
		  contactDetailsResponseDto.setContactId(contactDetails.getContactId());;
		  contactDetailsResponseDto.setContactName(contactDetails.getContactName());
		  contactDetailsResponseDto.setEmail(contactDetails.getEmail());
		  
		  return contactDetailsResponseDto;
		  
		 
		  
	 }
	 
	 public ContactDetails toEntity(ContactDetailsRequestDto contactDetailsRequestDto) {
		 
		 
		 ContactDetails contactDetails=new ContactDetails();
		 
		 contactDetails.setContactId(contactDetailsRequestDto.getContactId());
		 contactDetails.setContactName(contactDetailsRequestDto.getContactName());
		 contactDetails.setEmail(contactDetailsRequestDto.getEmail());
		 contactDetails.setPrimaryMobile(contactDetailsRequestDto.getPrimaryMobile());
		 contactDetails.setAlternateMobile(contactDetailsRequestDto.getAlternateMobile());
		 contactDetails.setProperty(contactDetailsRequestDto.getProperty());
		 
		 contactDetails.setPreferredContactMethod(contactDetailsRequestDto.getPreferredContactMethod());
		  return contactDetails;
	 }
	 
  }
