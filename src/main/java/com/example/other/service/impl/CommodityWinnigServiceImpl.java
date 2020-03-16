package com.example.other.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.other.entity.CommodityEntity;
import com.example.other.entity.CommodityWinnigEntity;
import com.example.other.mapper.CommodityMapper;
import com.example.other.mapper.CommodityWinnigMapper;
import com.example.other.service.CommodityService;
import com.example.other.service.CommodityWinnigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class CommodityWinnigServiceImpl extends ServiceImpl<CommodityWinnigMapper, CommodityWinnigEntity> implements CommodityWinnigService {
    @Resource
    private CommodityWinnigMapper commodityWinnigMapper;

    @Override
    public Page<CommodityWinnigEntity> pageList(Page<CommodityWinnigEntity> page, CommodityWinnigEntity commodityWinnigEntity) {
        return commodityWinnigMapper.pageList(page, commodityWinnigEntity);
    }
}
