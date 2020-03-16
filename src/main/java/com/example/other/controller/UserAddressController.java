package com.example.other.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.other.config.jwt.UserLoginToken;
import com.example.other.entity.UserAddressEntity;
import com.example.other.entity.dto.Error;
import com.example.other.entity.dto.ReturnMessageDto;
import com.example.other.service.UserAddressService;
import com.example.other.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@Api(tags = "用户")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @PostMapping("/add")
    @ApiOperation(value = "新增地址")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto add(@RequestBody UserAddressEntity userAddressEntity) {
        ReturnMessageDto returnMessageDto;
        UserAddressEntity serviceOne = userAddressService.getOne(new QueryWrapper<UserAddressEntity>().lambda().eq(UserAddressEntity::getIsDefault, UserAddressEntity.ISDEFAULT.YES.getName()).eq(UserAddressEntity::getUserId, userAddressEntity.getUserId()));
        if (userAddressEntity.getIsDefault().equals(UserAddressEntity.ISDEFAULT.YES.getName())) {
            if (serviceOne != null) {
                serviceOne.setIsDefault(UserAddressEntity.ISDEFAULT.NO.getName());
                userAddressService.updateById(serviceOne);
            }
        }
        if (serviceOne == null) {
            userAddressEntity.setIsDefault(UserAddressEntity.ISDEFAULT.YES.getName());
        }
        userAddressService.save(userAddressEntity);
        return returnMessageDto = new ReturnMessageDto(Error.INFO_200);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改地址")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto update(@RequestBody UserAddressEntity userAddressEntity) {
        ReturnMessageDto returnMessageDto;
        userAddressService.updateById(userAddressEntity);
        return returnMessageDto = new ReturnMessageDto(Error.INFO_200);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除地址")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto delete(@RequestBody UserAddressEntity userAddressEntity) {
        ReturnMessageDto returnMessageDto;
        userAddressService.removeById(userAddressEntity.getId());
        return returnMessageDto = new ReturnMessageDto(Error.INFO_200);
    }

    @PostMapping("/page")
    @ApiOperation(value = "地址列表")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto page(@RequestBody UserAddressEntity userAddressEntity) {
        List<UserAddressEntity> list = userAddressService.list(new QueryWrapper<UserAddressEntity>().lambda().eq(UserAddressEntity::getUserId, userAddressEntity.getUserId()));
        return new ReturnMessageDto(list, Error.INFO_200);
    }
}
