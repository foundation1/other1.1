package com.example.other.entity.dto;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.example.other.entity.dto.Error;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "返回响应数据")
public class ReturnMessageDto {
    @ApiModelProperty(value = "参数")
    private Object data;
    @ApiModelProperty(value = "是否成功")
    private String code;
    @ApiModelProperty(value = "错误信息")
    private String message;
    @ApiModelProperty(value = "数据条数")
    private Long count;
    @ApiModelProperty(value = "token")
    private String toKen;

    public ReturnMessageDto(JSON data, String toKen, Long count, Error error) {
        this.data = data;
        this.code = error.getCode();
        this.message = error.getValue();
        this.count = count;
        this.toKen = toKen;
    }

    public ReturnMessageDto(Object data, Long count, Error error) {
        this.data = data;
        this.code = error.getCode();
        this.message = error.getValue();
        this.count = count;
    }

    public ReturnMessageDto(Object data, Error error) {
        this.data = data;
        this.code = error.getCode();
        this.message = error.getValue();
    }

    public ReturnMessageDto(Error error) {
        this.code = error.getCode();
        this.message = error.getValue();
    }

    public ReturnMessageDto() {

    }
}
