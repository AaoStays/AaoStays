package com.aao.repo;

import com.aao.entity.Admin;
import com.aao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUser(User user);

    boolean existsByUser(User user);
}
