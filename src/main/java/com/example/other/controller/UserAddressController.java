package com.example.other.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.other.config.jwt.UserLoginToken;
import com.example.other.entity.UserAddressEntity;
import com.example.other.entity.UserEntity;
import com.example.other.entity.dto.Error;
import com.example.other.entity.dto.ReturnMessageDto;
import com.example.other.service.TokenService;
import com.example.other.service.UserAddressService;
import com.example.other.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/address")
@Api(tags = "地址管理")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/add")
    @ApiOperation(value = "新增地址")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto add(@RequestBody UserAddressEntity userAddressEntity, HttpServletRequest request) {
        UserEntity user = tokenService.getUser(request.getHeader("token"));
        userAddressEntity.setUserId(user.getId());
        UserAddressEntity serviceOne = userAddressService.getOne(new QueryWrapper<UserAddressEntity>().lambda().eq(UserAddressEntity::getIsDefault, UserAddressEntity.ISDEFAULT.YES.getName()).eq(UserAddressEntity::getUserId, user.getId()));
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
        return new ReturnMessageDto(Error.INFO_200);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改地址")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto update(@RequestBody UserAddressEntity userAddressEntity) {
        if (userAddressEntity.getIsDefault().equals(UserAddressEntity.ISDEFAULT.YES.getName())) {
            UserAddressEntity serviceOne = userAddressService.getOne(new QueryWrapper<UserAddressEntity>().lambda().eq(UserAddressEntity::getIsDefault, UserAddressEntity.ISDEFAULT.YES.getName()).ne(UserAddressEntity::getId, userAddressEntity.getId()).eq(UserAddressEntity::getUserId, userAddressEntity.getUserId()));
            if (serviceOne != null) {
                serviceOne.setIsDefault(UserAddressEntity.ISDEFAULT.NO.getName());
                userAddressService.updateById(serviceOne);
            }
        }
        userAddressService.updateById(userAddressEntity);
        return new ReturnMessageDto(Error.INFO_200);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除地址")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto delete(@RequestBody UserAddressEntity userAddressEntity) {
        userAddressService.removeById(userAddressEntity.getId());
        return new ReturnMessageDto(Error.INFO_200);
    }

    @PostMapping("/getid")
    @ApiOperation(value = "获取id地址")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto getid(@RequestBody UserAddressEntity userAddressEntity) {
        userAddressEntity = userAddressService.getById(userAddressEntity.getId());
        return new ReturnMessageDto(userAddressEntity, Error.INFO_200);
    }

    @PostMapping("/page")
    @ApiOperation(value = "地址列表")
    @ResponseBody
    @UserLoginToken
    private ReturnMessageDto page(@RequestBody UserAddressEntity userAddressEntity, HttpServletRequest httpServletRequest) {
        UserEntity user = tokenService.getUser(httpServletRequest.getHeader("token"));
        userAddressEntity.setUserId(user.getId());
        LambdaQueryWrapper<UserAddressEntity> eq = new QueryWrapper<UserAddressEntity>().lambda().eq(UserAddressEntity::getUserId, userAddressEntity.getUserId());
        if (StrUtil.hasEmpty(userAddressEntity.getIsDefault())) {
            eq.eq(UserAddressEntity::getIsDefault, userAddressEntity.getIsDefault());
        }
        List<UserAddressEntity> list = userAddressService.list(eq);
        return new ReturnMessageDto(list, Error.INFO_200);
    }
}
