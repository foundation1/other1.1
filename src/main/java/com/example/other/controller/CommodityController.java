package com.example.other.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.other.config.jwt.Config;
import com.example.other.config.jwt.UserLoginToken;
import com.example.other.entity.CommodityEntity;
import com.example.other.entity.UserAddressEntity;
import com.example.other.entity.UserEntity;
import com.example.other.entity.dto.Error;
import com.example.other.entity.dto.LotteryDto;
import com.example.other.entity.dto.ReturnMessageDto;
import com.example.other.service.CommodityService;
import com.example.other.service.TokenService;
import com.example.other.service.UserService;
import com.example.other.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Struct;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/commodity")
@Api(tags = "商品")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommodityController {
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/add", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "添加")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto add(@RequestParam(name = "file", required = false) MultipartFile file, CommodityEntity commodityEntity) {
        return commodityService.addAll(file, commodityEntity);
    }

    @PostMapping(value = "/update", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "修改")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto update(@RequestParam(name = "file", required = false) MultipartFile file, CommodityEntity commodityEntity) {
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

    @GetMapping("/page")
    @ApiOperation(value = "列表")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto page(Page page, CommodityEntity commodityEntity, HttpServletRequest httpServletRequest) {
        UserEntity user = tokenService.getUser(httpServletRequest.getHeader("token"));
        LambdaQueryWrapper<CommodityEntity> lambda = new QueryWrapper<CommodityEntity>().lambda();
        if (!StrUtil.hasEmpty(commodityEntity.getCommodity())) {
            lambda.like(CommodityEntity::getCommodity, commodityEntity.getCommodity());
        }
        if (!StrUtil.hasEmpty(commodityEntity.getTypeC())) {
            lambda.eq(CommodityEntity::getTypeC, commodityEntity.getTypeC());
        }
        page = commodityService.page(page, lambda);
        return new ReturnMessageDto(page.getRecords(), page.getTotal(), Error.INFO_200);
    }

    @PostMapping("/lottery")
    @ApiOperation(value = "抽奖")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto lottery(@RequestBody LotteryDto lotteryDto, HttpServletRequest request) {
        UserEntity user = tokenService.getUser(request.getHeader("token"));
        lotteryDto.setUserId(user.getId());
        return commodityService.lottery(lotteryDto);
    }
}
