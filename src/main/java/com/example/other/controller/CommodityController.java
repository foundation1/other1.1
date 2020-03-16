package com.example.other.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.other.config.jwt.Config;
import com.example.other.config.jwt.UserLoginToken;
import com.example.other.entity.CommodityEntity;
import com.example.other.entity.UserAddressEntity;
import com.example.other.entity.dto.Error;
import com.example.other.entity.dto.ReturnMessageDto;
import com.example.other.service.CommodityService;
import com.example.other.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private Config config;

    @PostMapping("/add")
    @ApiOperation(value = "添加")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto add(@RequestParam(name = "file", required = false) MultipartFile file, @RequestBody CommodityEntity commodityEntity) {
        String fileName = "";
        if (file != null) {
            Util util = new Util();
            Map<String, Object> map = util.upload(file, config.getFileUrl());
            if ((boolean) map.get("retuln")) {
                fileName = map.get("name").toString();
            }
        }
        commodityEntity.setImgPath(fileName);
        commodityService.save(commodityEntity);
        return new ReturnMessageDto(Error.INFO_200);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto update(@RequestParam(name = "file", required = false) MultipartFile file, @RequestBody CommodityEntity commodityEntity) {
        ReturnMessageDto returnMessageDto;
        if (file != null) {
            Util util = new Util();
            Map<String, Object> map = util.upload(file, config.getFileUrl());
            if ((boolean) map.get("retuln")) {
                commodityEntity.setImgPath(map.get("name").toString());
            }
        }
        commodityService.updateById(commodityEntity);
        return returnMessageDto = new ReturnMessageDto(Error.INFO_200);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除地址")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto delete(@RequestBody CommodityEntity commodityEntity) {
        commodityService.removeById(commodityEntity.getId());
        return new ReturnMessageDto(Error.INFO_200);
    }

    @PostMapping("/page")
    @ApiOperation(value = "地址列表")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto page(@RequestBody CommodityEntity commodityEntity) {
        List<CommodityEntity> list = commodityService.list(new QueryWrapper<CommodityEntity>());
        return new ReturnMessageDto(list, Error.INFO_200);
    }
}
