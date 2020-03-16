package com.example.other.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.other.entity.CommodityEntity;
import com.example.other.entity.UserAddressEntity;
import com.example.other.mapper.CommodityMapper;
import com.example.other.mapper.UserAddressMapper;
import com.example.other.service.CommodityService;
import com.example.other.service.UserAddressService;
import org.springframework.stereotype.Service;


@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, CommodityEntity> implements CommodityService {
}
