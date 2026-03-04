package com.datngoc.wms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datngoc.wms.entity.User;

public interface UserRespository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
