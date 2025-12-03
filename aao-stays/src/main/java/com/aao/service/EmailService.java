package com.aao.service;


import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailService {

	@Value("${resend.api.key}")
	private String resendApiKey;
	
    private static final String RESEND_URL="https://api.resend.com/emails";
    
    public void sendEmailVerificationLink(String toEmail, String verificationLink) {
    	
    	RestTemplate rest= new RestTemplate();
    	
    	Map<String,Object> body=Map.of(
    			 "from", "AaoStays <onboarding@resend.dev>",
                 "to", new String[]{toEmail},
                 "subject", "Verify your email",
                 "html", "<p>Click below to verify:</p><a href=\"" + verificationLink + "\">Verify Email</a>"
    			);
    	

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(resendApiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);	

        rest.exchange(RESEND_URL, HttpMethod.POST, request, String.class);
        
    }
}
