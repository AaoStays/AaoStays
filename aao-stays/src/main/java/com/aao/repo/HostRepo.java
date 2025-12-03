package com.aao.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aao.entity.Host;
import java.util.Optional;

public interface HostRepo extends JpaRepository<Host, Long> {
    boolean existsByUser_Id(Long userId);

    Optional<Host> findByUser_Id(Long userId);
}
