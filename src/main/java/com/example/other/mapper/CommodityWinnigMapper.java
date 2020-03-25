package com.example.other.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.other.entity.CommodityEntity;
import com.example.other.entity.CommodityWinnigEntity;
import org.apache.ibatis.annotations.Param;


public interface CommodityWinnigMapper extends BaseMapper<CommodityWinnigEntity> {
    Page<CommodityWinnigEntity> pageList(Page<CommodityWinnigEntity> page, @Param("entity") CommodityWinnigEntity commodityWinnigEntity, @Param("status") String status);
}
