package com.example.other.entity.dto;

public enum Error {
    INFO_200("200", "请求成功"),
    INFO_201("201", "用户不存在!"),
    INFO_202("202", "密码错误!"),
    INFO_203("203", "登录信息已经失效，请重新登录!"),
    INFO_204("204", "用户名重复!"),
    INFO_205("205", "图片写入失败!"),
    INFO_206("206", "每种类型最多可添加8条数据!"),
    INFO_207("207", "金币不足请充值!"),
    ;
    // 成员变量
    private String code;
    private String value;

    // 构造方法
    Error(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }
}
