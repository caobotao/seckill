package com.cbt.seckill.service;

import com.cbt.seckill.error.BusinessException;
import com.cbt.seckill.service.model.UserModel;

public interface UserService {
    UserModel getUserById(Integer id) throws BusinessException;

    void register(UserModel model) throws BusinessException;

    UserModel validateLogin(String telphone, String encrptPassword) throws BusinessException;
}
