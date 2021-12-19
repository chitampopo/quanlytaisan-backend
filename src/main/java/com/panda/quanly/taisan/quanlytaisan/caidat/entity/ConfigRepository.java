package com.panda.quanly.taisan.quanlytaisan.caidat.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConfigRepository extends JpaRepository<Config, Long>{
    
    List<Config> findByKey(String key);
}
