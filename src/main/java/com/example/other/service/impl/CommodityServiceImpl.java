package com.example.other.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.other.config.jwt.Config;
import com.example.other.entity.CommodityEntity;
import com.example.other.entity.CommodityWinnigEntity;
import com.example.other.entity.UserAddressEntity;
import com.example.other.entity.UserEntity;
import com.example.other.entity.dto.Error;
import com.example.other.entity.dto.LotteryDto;
import com.example.other.entity.dto.ReturnMessageDto;
import com.example.other.mapper.CommodityMapper;
import com.example.other.mapper.UserAddressMapper;
import com.example.other.service.CommodityService;
import com.example.other.service.UserAddressService;
import com.example.other.service.UserService;
import com.example.other.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, CommodityEntity> implements CommodityService {
    @Autowired
    private Config config;
    @Autowired
    private UserService userService;
    @Autowired
    private CommodityService commodityService;

    @Override
    public ReturnMessageDto addAll(MultipartFile file, CommodityEntity commodityEntity) {
        if (StrUtil.hasEmpty(commodityEntity.getTypeC())) {
            commodityEntity.setTypeC(CommodityEntity.TYPEC.OTHER.getName());
        } else {
            if (!commodityEntity.getTypeC().equals(CommodityEntity.TYPEC.OTHER.getName())) {
                List<CommodityEntity> list = list(new QueryWrapper<CommodityEntity>().lambda().eq(CommodityEntity::getTypeC, commodityEntity.getTypeC()));
                if (list.size() == 8) {
                    return new ReturnMessageDto(Error.INFO_206);
                }
            }
        }
        String fileName = "";
        if (file != null) {
            Util util = new Util();
            Map<String, Object> map = util.upload(file, config.getFileUrl());
            if ((boolean) map.get("retuln")) {
                fileName = map.get("name").toString();
            } else {
                return new ReturnMessageDto(Error.INFO_205);
            }
        }
        commodityEntity.setImgPath(fileName);
        commodityEntity.setPrice(commodityEntity.getPrice().multiply(new BigDecimal("100")));
        save(commodityEntity);
        return new ReturnMessageDto(Error.INFO_200);
    }

    @Override
    public ReturnMessageDto updateAll(MultipartFile file, CommodityEntity commodityEntity) {
        if (StrUtil.hasEmpty(commodityEntity.getTypeC())) {
            commodityEntity.setTypeC(CommodityEntity.TYPEC.OTHER.getName());
        } else {
            if (!commodityEntity.getTypeC().equals(CommodityEntity.TYPEC.OTHER.getName())) {
                List<CommodityEntity> list = list(new QueryWrapper<CommodityEntity>().lambda().eq(CommodityEntity::getTypeC, commodityEntity.getTypeC()));
                if (list.size() == 8) {
                    return new ReturnMessageDto(Error.INFO_206);
                }
            }
        }
        if (file != null) {
            Util util = new Util();
            Map<String, Object> map = util.upload(file, config.getFileUrl());
            if ((boolean) map.get("retuln")) {
                commodityEntity.setImgPath(map.get("name").toString());
            }
        }
        updateById(commodityEntity);
        return new ReturnMessageDto(Error.INFO_200);
    }

    @Override
    public ReturnMessageDto lottery(LotteryDto lotteryDto) {
        ReturnMessageDto returnMessageDto;
        UserEntity userEntity = userService.getById(lotteryDto.getUserId());
        BigDecimal bigDecimal = BigDecimal.ZERO;
        switch (lotteryDto.getTypeC()) {
            case "2":
                bigDecimal = new BigDecimal("50");
                break;
            case "3":
                bigDecimal = new BigDecimal("100");
                break;
            case "4":
                bigDecimal = new BigDecimal("200");
                break;
        }
        if (userEntity.getMoney().compareTo(bigDecimal) == -1) {
            return new ReturnMessageDto(Error.INFO_207);
        }
        List<CommodityEntity> list = commodityService.list(new QueryWrapper<CommodityEntity>().lambda().eq(CommodityEntity::getTypeC, lotteryDto.getTypeC()));
        CommodityEntity commodityEntity = Util.lotterAlgorithm(list);
        if (commodityEntity != null) {
            userEntity.setMoney(userEntity.getMoney().subtract(bigDecimal));
            userService.updateById(userEntity);
            returnMessageDto = new ReturnMessageDto(commodityEntity, Error.INFO_200);
        } else {
            returnMessageDto = new ReturnMessageDto(Error.INFO_208);
        }

        return returnMessageDto;
    }
}
