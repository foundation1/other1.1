package com.example.other.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.other.entity.UserCodeEntity;
import com.example.other.entity.dto.ReturnMessageDto;
import com.example.other.entity.UserEntity;
import com.example.other.entity.dto.Error;
import com.example.other.service.TokenService;
import com.example.other.service.UserCodeService;
import com.example.other.service.UserService;
import com.example.other.config.jwt.UserLoginToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
@RequestMapping("/user")
@Api(tags = "用户")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LoginController {
    @Resource
    UserService userService;
    @Resource
    TokenService tokenService;
    @Autowired
    private UserCodeService userCodeService;

    //登录
    @PostMapping("/login")
    @ApiOperation(value = "登录接口")
    @ResponseBody
    public ReturnMessageDto login(@RequestBody UserEntity userEntity) {
        ReturnMessageDto returnMessageDto;
        UserEntity userServiceOne = userService.getOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getUserName, userEntity.getUserName()));
        if (userServiceOne == null) {
            returnMessageDto = new ReturnMessageDto(Error.INFO_201);
            return returnMessageDto;
        } else {
            String md5 = SecureUtil.md5(userEntity.getPwd());
            if (!userServiceOne.getPwd().equals(md5)) {
                returnMessageDto = new ReturnMessageDto(Error.INFO_202);
                return returnMessageDto;
            } else {
                String token = tokenService.getToken(userServiceOne);
                returnMessageDto = new ReturnMessageDto(JSONUtil.parse(userServiceOne), token, 0L, Error.INFO_200);
                return returnMessageDto;
            }
        }
    }

    //@UserLoginToken
    @PostMapping("/signIn")
    @ApiOperation(value = "注册,4个参数都需要传")
    @ResponseBody
    public ReturnMessageDto signIn(@RequestBody UserEntity userEntity) {
        UserEntity entity = userService.getOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getUserName, userEntity.getUserName()));
        if (entity != null) {
            new ReturnMessageDto(Error.INFO_204);
        }
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        long id = snowflake.nextId();
        if (!StrUtil.hasEmpty(userEntity.getInvitedYards())) {
            UserCodeEntity userCodeEntity = new UserCodeEntity();
            userCodeEntity.setUserId(id);
            userCodeEntity.setInvitationCode(userEntity.getInvitedYards());
            userCodeService.save(userCodeEntity);
        }
        userEntity.setMoney(BigDecimal.ZERO);
        userEntity.setPwd(SecureUtil.md5(userEntity.getPwd()));
        userEntity.setType(UserEntity.TYPE.USER.getName());
        userEntity.setInvitationCode(String.valueOf(System.currentTimeMillis()));
        userEntity.setId(id);
        userService.save(userEntity);
        return new ReturnMessageDto(Error.INFO_200);
    }

    @PostMapping("/update/pwd")
    @ApiOperation(value = "修改密码,通过用户名和密码修改")
    @ResponseBody
    public ReturnMessageDto updatePwd(@RequestBody UserEntity userEntity) {
        UserEntity entity = userService.getOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getUserName, userEntity.getUserName()));
        if (entity == null) {
            return new ReturnMessageDto(Error.INFO_201);
        }
        entity.setPwd(SecureUtil.md5(userEntity.getPwd()));
        userService.updateById(entity);
        return new ReturnMessageDto(Error.INFO_200);
    }


    @PostMapping("/get")
    @ApiOperation(value = "通过用户名查询用户所有信息，请前端自己进行判断，如果通过用户重新填入密码，请求修改密码接口")
    @ResponseBody
    public ReturnMessageDto get(@RequestBody UserEntity userEntity) {
        ReturnMessageDto returnMessageDto;
        UserEntity entity = userService.getOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getUserName, userEntity.getUserName()));
        if (entity == null) {
            return returnMessageDto = new ReturnMessageDto(Error.INFO_201);
        }
        return returnMessageDto = new ReturnMessageDto(entity, Error.INFO_200);
    }

    @GetMapping("/page")
    @ApiOperation(value = "用户列表")
    @ResponseBody
    @UserLoginToken
    public ReturnMessageDto userList(Page<UserEntity> page,UserEntity userEntity) {
        LambdaQueryWrapper<UserEntity> eq = new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getType, UserEntity.TYPE.USER.getName());
        if (!StrUtil.hasEmpty(userEntity.getUserName())) {
            eq.like(UserEntity::getUserName, userEntity.getUserName());
        }
        page = userService.page(page, eq);
        return new ReturnMessageDto(page.getRecords(), page.getTotal(), Error.INFO_200);
    }
}
