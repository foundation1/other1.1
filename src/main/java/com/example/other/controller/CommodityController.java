package com.example.other.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.other.config.jwt.Config;
import com.example.other.config.jwt.UserLoginToken;
import com.example.other.entity.CommodityEntity;
import com.example.other.entity.UserAddressEntity;
import com.example.other.entity.UserEntity;
import com.example.other.entity.dto.Error;
import com.example.other.entity.dto.LotteryDto;
import com.example.other.entity.dto.ReturnMessageDto;
import com.example.other.service.CommodityService;
import com.example.other.service.UserService;
import com.example.other.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/commodity")
@Api(tags = "删除")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommodityController {
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @ApiOperation(value = "添加")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto add(@RequestParam(name = "file", required = false) MultipartFile file, @RequestBody CommodityEntity commodityEntity) {
        return commodityService.addAll(file, commodityEntity);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto update(@RequestParam(name = "file", required = false) MultipartFile file, @RequestBody CommodityEntity commodityEntity) {
        return commodityService.updateAll(file, commodityEntity);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto delete(@RequestBody CommodityEntity commodityEntity) {
        commodityService.removeById(commodityEntity.getId());
        return new ReturnMessageDto(Error.INFO_200);
    }

    @PostMapping("/page")
    @ApiOperation(value = "列表")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto page(@RequestBody CommodityEntity commodityEntity) {
        LambdaQueryWrapper<CommodityEntity> lambda = new QueryWrapper<CommodityEntity>().lambda();
        if (!commodityEntity.getTypeC().equals(CommodityEntity.TYPEC.OTHER.getName())) {
            lambda.eq(CommodityEntity::getTypeC, commodityEntity.getTypeC());
        }
        List<CommodityEntity> list = commodityService.list(lambda);
        return new ReturnMessageDto(list, Error.INFO_200);
    }

    @PostMapping("/lottery")
    @ApiOperation(value = "抽奖")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto lottery(@RequestBody LotteryDto lotteryDto) {
        return commodityService.lottery(lotteryDto);
    }
}
