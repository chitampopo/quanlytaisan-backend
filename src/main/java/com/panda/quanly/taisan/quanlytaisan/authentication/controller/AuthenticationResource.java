package com.panda.quanly.taisan.quanlytaisan.authentication.controller;

import com.panda.quanly.taisan.quanlytaisan.authentication.entity.AuthenticationData;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationResource {
    
    @GetMapping("/greeting")
    public Object greeting() {
        return "{\"id\":1,\"content\":\"Hello, World!\"}";
    }

    @PostMapping("/login")
    public AuthenticationData login(String username, String password) {
        return new AuthenticationData();
    }
}
