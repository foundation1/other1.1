package com.example.other;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.other.entity.CommodityWinnigEntity;
import com.example.other.entity.dto.Error;
import com.example.other.mapper.CommodityWinnigMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class OtherApplicationTests {
    @Resource
    private CommodityWinnigMapper commodityWinnigMapper;


    @Test
    public void xiaohui() {
        List<CommodityWinnigEntity> commodityWinnigEntities = commodityWinnigMapper.selectList(new QueryWrapper<>());
        System.out.println(commodityWinnigEntities);
    }
}
