package com.example.other.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.other.entity.CommodityWinnigEntity;
import com.example.other.entity.UserAddressEntity;
import org.apache.ibatis.annotations.Param;


public interface CommodityWinnigService extends IService<CommodityWinnigEntity> {

    /**
     * 重写分页查询方法
     *
     * @param
     * @return
     * @Author xh
     * @Date 2020/3/16
     * @Time 16:07
     **/
    Page<CommodityWinnigEntity> pageList(Page<CommodityWinnigEntity> page, @Param("entity") CommodityWinnigEntity commodityWinnigEntity);
}
