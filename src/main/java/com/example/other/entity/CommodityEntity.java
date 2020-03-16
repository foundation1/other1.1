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
    //插入时间
    @TableField(value = "creation_time")
    private String creationTime;
    //更新时间
    @TableField(value = "update_time")
    private String updateTime;
}
