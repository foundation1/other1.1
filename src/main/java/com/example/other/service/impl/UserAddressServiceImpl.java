package com.example.other.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.other.entity.UserAddressEntity;
import com.example.other.entity.UserCodeEntity;
import com.example.other.mapper.UserAddressMapper;
import com.example.other.mapper.UserCodeMapper;
import com.example.other.service.UserAddressService;
import com.example.other.service.UserCodeService;
import org.springframework.stereotype.Service;


@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddressEntity> implements UserAddressService {
}
