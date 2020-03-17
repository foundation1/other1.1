package com.example.other.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.other.config.jwt.UserLoginToken;
import com.example.other.entity.CommodityWinnigEntity;
import com.example.other.entity.UserEntity;
import com.example.other.entity.dto.Error;
import com.example.other.entity.dto.ReturnMessageDto;
import com.example.other.service.CommodityWinnigService;
import com.example.other.service.impl.CommodityWinnigServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @GetMapping("/list")
    @ApiOperation(value = "获取列表")
    @ResponseBody
    @UserLoginToken
    public ReturnMessageDto userList(Page page, CommodityWinnigEntity commodityWinnigEntity) {
        page = commodityWinnigService.pageList(page, commodityWinnigEntity);
        return new ReturnMessageDto(page.getRecords(), page.getCurrent(), Error.INFO_200);
    }

    @GetMapping("/save")
    @ApiOperation(value = "添加")
    @ResponseBody
    @UserLoginToken
    public ReturnMessageDto userList(@RequestBody CommodityWinnigEntity commodityWinnigEntity) {
        commodityWinnigService.save(commodityWinnigEntity);
        return new ReturnMessageDto(Error.INFO_200);
    }
}
