package com.example.other.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.other.entity.CommodityEntity;
import com.example.other.entity.UserAddressEntity;
import com.example.other.entity.dto.LotteryDto;
import com.example.other.entity.dto.ReturnMessageDto;
import org.springframework.web.multipart.MultipartFile;


public interface CommodityService extends IService<CommodityEntity> {
    /**
     * 保存数据
     *
     * @param file
     * @param commodityEntity
     * @return
     * @Author xh
     * @Date 2020/3/17
     * @Time 18:59
     **/
    ReturnMessageDto addAll(MultipartFile file, CommodityEntity commodityEntity);

    /**
     * 修改数据
     *
     * @param file
     * @param commodityEntity
     * @return
     * @Author xh
     * @Date 2020/3/17
     * @Time 18:59
     **/
    ReturnMessageDto updateAll(MultipartFile file, CommodityEntity commodityEntity);

    /**
     * 抽奖
     *
     * @param lotteryDto
     * @return
     * @Author xh
     * @Date 2020/3/17
     * @Time 19:00
     **/
    ReturnMessageDto lottery(LotteryDto lotteryDto);
}
