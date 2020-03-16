package com.example.other.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.other.entity.UserEntity;
import com.example.other.mapper.UserMapper;
import com.example.other.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
}
