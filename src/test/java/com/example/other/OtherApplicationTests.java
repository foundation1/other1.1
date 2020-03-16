package com.example.other;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.other.entity.CommodityEntity;
import com.example.other.entity.CommodityWinnigEntity;
import com.example.other.entity.dto.Error;
import com.example.other.mapper.CommodityWinnigMapper;
import com.example.other.util.Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class OtherApplicationTests {
    @Resource
    private CommodityWinnigMapper commodityWinnigMapper;


    @Test
    public void xiaohui() {
        CommodityEntity commodityEntity = new CommodityEntity();
        commodityEntity.setId(1L);
        commodityEntity.setProbability(20D);
        List<CommodityEntity> list = new ArrayList<>();
        list.add(commodityEntity);
        commodityEntity.setProbability(30D);
        commodityEntity.setId(2L);
        list.add(commodityEntity);
        commodityEntity.setProbability(40D);
        commodityEntity.setId(3L);
        list.add(commodityEntity);
        commodityEntity.setProbability(50D);
        commodityEntity.setId(1L);
        for (int i = 0; i < 100; i++) {
            CommodityEntity commodityEntity1 = Util.lotterAlgorithm(list);
            System.out.print(commodityEntity1.getId() + "--" + i);
        }

    }
}
