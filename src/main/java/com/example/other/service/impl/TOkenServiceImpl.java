package com.example.other.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.other.entity.UserEntity;
import com.example.other.service.TokenService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TOkenServiceImpl implements TokenService {
    @Override
    public String getToken(UserEntity userEntity) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60 * 60 * 1000;//一小时有效时间
        Date end = new Date(currentTime);
        String token = "";
        token = JWT.create().withAudience(String.valueOf(userEntity.getId())).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(userEntity.getPwd()));
        return token;
    }
}
