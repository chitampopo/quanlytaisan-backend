package com.panda.quanly.taisan.quanlytaisan.authentication.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.panda.quanly.taisan.quanlytaisan.authentication.entity.Authentication;
import com.panda.quanly.taisan.quanlytaisan.authentication.entity.AuthenticationData;
import com.panda.quanly.taisan.quanlytaisan.authentication.entity.AuthenticationDataRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/authen")
public class AuthenticationResource {
    
    @Autowired AuthenticationDataRepo authenRepo;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationData loginData) {
        List<Authentication> findByUsername = authenRepo.findByUsername(loginData.getUsername());
        if(findByUsername.get(0) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String encodePassword = getMD5EncryptedValue(loginData.getPassword());
        if(encodePassword.equals(findByUsername.get(0).getPassword())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public String getMD5EncryptedValue(String password) {
        final byte[] defaultBytes = password.getBytes();
        try {
            final MessageDigest md5MsgDigest = MessageDigest.getInstance("MD5");
            md5MsgDigest.reset();
            md5MsgDigest.update(defaultBytes);
            final byte messageDigest[] = md5MsgDigest.digest();

            final StringBuffer hexString = new StringBuffer();
            for (final byte element : messageDigest) {
                final String hex = Integer.toHexString(0xFF & element);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            password = hexString + "";
        } catch (final NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        return password;
    }
}
