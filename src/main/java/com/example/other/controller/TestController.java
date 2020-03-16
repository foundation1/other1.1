package com.example.other.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.example.other.config.jwt.Config;
import com.example.other.entity.dto.Error;
import com.example.other.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping
@Api(tags = "用户")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController {
    @Autowired
    private Config config;

    @PostMapping("/upload")
    public void upload(@RequestParam(name = "file", required = false) MultipartFile file) {
        Util util = new Util();
        util.upload(file,config.getFileUrl());
    }
}
