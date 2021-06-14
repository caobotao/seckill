package com.cbt.seckill.dataobject;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "user_password")
public class UserPasswordDO {
    @Id
    private Integer id;

    private String encrptPassword;

    private Integer userId;

}