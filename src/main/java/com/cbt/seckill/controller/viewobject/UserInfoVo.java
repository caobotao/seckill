package com.cbt.seckill.controller.viewobject;

import lombok.Data;

@Data
public class UserInfoVo {
    private Integer id;

    private String name;

    private Byte gender;

    private Integer age;

    private String telphone;
}
