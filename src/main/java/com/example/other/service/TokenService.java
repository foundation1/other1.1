package com.example.other.service;

import com.example.other.entity.UserEntity;
import org.springframework.stereotype.Service;


public interface TokenService {
    String getToken(UserEntity userEntity);

    UserEntity getUser(String toKen);
}
