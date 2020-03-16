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

@Data
@TableName(value = "u_address")
public class UserAddressEntity extends Model<UserAddressEntity> {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(hidden = true)
    private Long id;
    private String name;
    private String tel;
    private String province;
    private String city;
    private String county;
    @TableField(value = "address_detail")
    private String addressDetail;
    @TableField(value = "area_code")
    private String areaCode;
    @TableField(value = "postal_code")
    private String postalCode;
    @TableField(value = "is_default")
    private String isDefault;
    @TableField(value = "u_id")
    private String userId;
    //插入时间
    @TableField(value = "creation_time")
    private String creationTime;
    //更新时间
    @TableField(value = "update_time")
    private String updateTime;

    public enum ISDEFAULT {
        NO("false", "否"),
        YES("true", "是"),
        ;
        // 成员变量
        private String name;
        private String value;

        // 构造方法
        private ISDEFAULT(String name, String value) {
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
