package com.aao.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aao.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
	
}
