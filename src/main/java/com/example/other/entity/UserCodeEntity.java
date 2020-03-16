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
@TableName(value = "u_code")
public class UserCodeEntity extends Model<UserEntity> {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(hidden = true)
    private Long id;
    @TableField(value = "u_id")
    private Long userId;
    @TableField(value = "invitation_code")
    private String invitationCode;
    //插入时间
    @TableField(value = "creation_time")
    private String creationTime;
    //更新时间
    @TableField(value = "update_time")
    private String updateTime;
}
