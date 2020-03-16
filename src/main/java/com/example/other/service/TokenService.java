package com.example.other.service;

import com.example.other.entity.UserEntity;
import org.springframework.stereotype.Service;


public interface TokenService {
    public String getToken(UserEntity userEntity);
}
