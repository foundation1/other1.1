package com.example.other.util;

import com.example.other.config.jwt.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Util {
    @Autowired
    private Config config;

    public Map<String, Object> upload(MultipartFile file, String path) {
        Map<String, Object> map = new HashMap<>(2);
        if (file == null) {
            map.put("retuln", false);
            return map;
        }
        if (file.getSize() > 1024 * 1024 * 10) {
            map.put("retuln", false);
            return map;
        }
        //获取文件后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
            map.put("retuln", false);
            return map;
        }
        File savePathFile = new File(path);
        if (!savePathFile.exists()) {
            //若不存在该目录，则创建目录
            savePathFile.mkdir();
        }
        //通过UUID生成唯一文件名
        String filename = UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
        try {
            //将文件保存指定目录
            file.transferTo(new File(path + filename));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("retuln", false);
            return map;
        }
        //返回文件名称
        map.put("retuln", true);
        map.put("name", "/img/" + filename);
        return map;
    }
}
