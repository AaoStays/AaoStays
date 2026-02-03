package com.aao.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aao.entity.ContactDetails;

public interface ContactDetailsRepo extends JpaRepository<ContactDetails, Long> {
	
	 boolean existsByEmail(String email);
	 
	 boolean existsByprimaryMobile(String primaryMobile);
	 
	 boolean existsByprimaryMobileOrEmail(String email, String primaryMobile);

}
