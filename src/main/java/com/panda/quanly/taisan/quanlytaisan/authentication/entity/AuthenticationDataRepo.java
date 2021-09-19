package com.panda.quanly.taisan.quanlytaisan.authentication.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationDataRepo extends JpaRepository<Authentication, Long> {
    List<Authentication> findByUsername(String name);
}
