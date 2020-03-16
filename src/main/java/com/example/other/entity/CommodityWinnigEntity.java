package com.example.other.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.example.other.entity.dto.CommodityDto;
import com.example.other.entity.dto.UserAddressDto;
import com.example.other.entity.dto.UserDto;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName(value = "c_winning")
public class CommodityWinnigEntity extends Model<CommodityWinnigEntity> {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long userId;
    private Long commodityId;
    private Long addressId;
    private String platform;
    private String number;
    private String status;
    //插入时间
    private String creationTime;
    //更新时间
    private String updateTime;
    private BigDecimal gold;
    private String statusC;
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String tel;
    @TableField(exist = false)
    private String province;
    @TableField(exist = false)
    private String city;
    @TableField(exist = false)
    private String county;
    @TableField(exist = false)
    private String addressDetail;
    @TableField(exist = false)
    private String areaCode;
    @TableField(exist = false)
    private String postalCode;
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String commodityIntro;
    @TableField(exist = false)
    private String commodity;
    @TableField(exist = false)
    private String imgPath;
    @TableField(exist = false)
    private BigDecimal price;
    @TableField(exist = false)
    private BigDecimal goldP;

    public enum STATUS {
        WINNING("W", "中奖"),
        PURCHASE("P", "购买"),
        ;
        // 成员变量
        private String name;
        private String value;

        // 构造方法
        private STATUS(String name, String value) {
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
