package com.example.other.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.other.config.jwt.UserLoginToken;
import com.example.other.entity.CommodityWinnigEntity;
import com.example.other.entity.UserEntity;
import com.example.other.entity.dto.Error;
import com.example.other.entity.dto.ReturnMessageDto;
import com.example.other.service.CommodityWinnigService;
import com.example.other.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * [Description]:
 *
 * @author : xh
 * @date : 2020-03-16 16:05
 */
@RestController
@RequestMapping("/winnig")
@Api(tags = "购买或中奖")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommodityWinnigController {
    @Resource
    private CommodityWinnigService commodityWinnigService;
    @Autowired
    private TokenService tokenService;

    @GetMapping("/list")
    @ApiOperation(value = "获取列表")
    @ResponseBody
    @UserLoginToken
    public ReturnMessageDto userList(Page page, CommodityWinnigEntity commodityWinnigEntity, HttpServletRequest httpServletRequest) {
        UserEntity user = tokenService.getUser(httpServletRequest.getHeader("token"));
        page = commodityWinnigService.pageList(page, commodityWinnigEntity, user);
        return new ReturnMessageDto(page.getRecords(), page.getTotal(), Error.INFO_200);
    }

    @GetMapping("/save")
    @ApiOperation(value = "添加")
    @ResponseBody
    @UserLoginToken
    public ReturnMessageDto save(@RequestBody CommodityWinnigEntity commodityWinnigEntity, HttpServletRequest httpServletRequest) {
        UserEntity user = tokenService.getUser(httpServletRequest.getHeader("token"));
        commodityWinnigEntity.setUserId(user.getId());
        commodityWinnigService.save(commodityWinnigEntity);
        return new ReturnMessageDto(Error.INFO_200);
    }

    @GetMapping("/update")
    @ApiOperation(value = "修改,如果是修改地址记得把地址id传给我")
    @ResponseBody
    @UserLoginToken
    public ReturnMessageDto update(@RequestBody CommodityWinnigEntity commodityWinnigEntity) {
        CommodityWinnigEntity entity = commodityWinnigService.getById(commodityWinnigEntity.getId());
        if (!StrUtil.hasEmpty(commodityWinnigEntity.getAddress())) {
            if (commodityWinnigEntity.getDeliverGoods().equals("1")) {
                return new ReturnMessageDto(Error.INFO_209);
            }
            entity.setAddressId(commodityWinnigEntity.getAddressId());
            entity.setAddress(commodityWinnigEntity.getAddress());
        }
        if (!StrUtil.hasEmpty(commodityWinnigEntity.getPlatform())) {
            entity.setPlatform(commodityWinnigEntity.getPlatform());
        }
        if (!StrUtil.hasEmpty(commodityWinnigEntity.getNumber())) {
            entity.setNumber(commodityWinnigEntity.getNumber());
        }
        if (!StrUtil.hasEmpty(commodityWinnigEntity.getDeliverGoods())) {
            entity.setDeliverGoods(commodityWinnigEntity.getDeliverGoods());
        }
        commodityWinnigService.updateById(entity);
        return new ReturnMessageDto(Error.INFO_200);
    }
}
