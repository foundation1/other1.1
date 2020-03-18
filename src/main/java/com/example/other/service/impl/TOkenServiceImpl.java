package com.example.other.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.other.entity.UserEntity;
import com.example.other.service.TokenService;
import com.example.other.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TOkenServiceImpl implements TokenService {
    @Autowired
    private UserService userService;

    @Override
    public String getToken(UserEntity userEntity) {
        Date start = new Date();
        //一小时有效时间
        long currentTime = System.currentTimeMillis() + 60 * 60 * 1000;
        Date end = new Date(currentTime);
        String token = "";
        token = JWT.create().withAudience(String.valueOf(userEntity.getId())).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(userEntity.getPwd()));
        return token;
    }

    @Override
    public UserEntity getUser(String toKen) {
        String userId = JWT.decode(toKen).getAudience().get(0);
        UserEntity user = userService.getById(userId);
        return user;
    }
}
