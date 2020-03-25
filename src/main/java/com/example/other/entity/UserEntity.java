package com.example.other.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName(value = "user")
@ApiModel(description = "用户实体类")
public class UserEntity extends Model<UserEntity> {
    private static final long serialVersionUID = 1L;
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(hidden = true)
    private Long id;
    @ApiModelProperty(value = "用户名")
    @TableField(value = "user_name")
    private String userName;
    @ApiModelProperty(value = "密码", name = "pwd")
    private String pwd;
    //插入时间
    @ApiModelProperty(hidden = true)
    @TableField(value = "creation_time")
    private String creationTime;
    //更新时间
    @ApiModelProperty(hidden = true)
    @TableField(value = "update_time")
    private String updateTime;
    //类型
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String type;
    //金币
    @ApiModelProperty(hidden = true)
    private BigDecimal money;
    //问题
    @ApiModelProperty(value = "问题")
    private String issue;
    //答案
    @ApiModelProperty(value = "答案")
    private String answer;
    //邀请码
    @ApiModelProperty(value = "邀请码,用户自己的")
    @TableField(value = "invitation_code")
    private String invitationCode;
    @ApiModelProperty(value = "邀请码,邀请人的，用于注册填入")
    @TableField(exist = false)
    private String InvitedYards;

    public enum TYPE {
        ADMIN("A", "管理员"),
        USER("U", "用户"),
        ;
        // 成员变量
        private String name;
        private String value;

        // 构造方法
        private TYPE(String name, String value) {
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
