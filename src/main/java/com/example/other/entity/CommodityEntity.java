package com.example.other.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName(value = "c_commodity")
public class CommodityEntity extends Model<CommodityEntity> {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(hidden = true)
    private Long id;
    private String commodityIntro;
    private String commodity;
    private String imgPath;
    private BigDecimal price;
    private Integer amount;
    private String typeC;
    private Double probability;
    private BigDecimal gold;
    //插入时间
    private String creationTime;
    //更新时间
    private String updateTime;
    public enum TYPEC {
        OTHER("1", "购买"),
        FIFTY("2", "50"),
        ONEHUNDRED("3", "100"),
        TWOHUNDRED("4", "200"),
        ;
        // 成员变量
        private String name;
        private String value;

        // 构造方法
        private TYPEC(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return this.name;
        }

        public String getValue() {
            return this.value;
        }
    }
}
