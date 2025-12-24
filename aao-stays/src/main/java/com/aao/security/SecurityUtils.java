package com.aao.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aao.entity.Host;
import com.aao.repo.HostRepo;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
@Transactional
public class SecurityUtils {
    
	 private  final HostRepo hostRepo;
     
	 public Long getCurrentUserId() {
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        return (Long) auth.getDetails(); 
	    }

	    public Long getCurrentHostId() {
	        Long userId = getCurrentUserId();

	        return hostRepo.findByUser_Id(userId)
	                .map(Host::getHostId)
	                .orElseThrow(() ->
	                        new IllegalStateException("User is not onboarded as Host")
	                );
	    }
}
