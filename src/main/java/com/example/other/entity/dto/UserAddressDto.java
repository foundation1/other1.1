package com.example.other.entity.dto;

import lombok.Data;

@Data
public class UserAddressDto {
    private String name;
    private String tel;
    private String province;
    private String city;
    private String county;
    private String addressDetail;
    private String areaCode;
    private String postalCode;
}
