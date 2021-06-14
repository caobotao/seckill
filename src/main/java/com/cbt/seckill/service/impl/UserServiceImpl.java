package com.cbt.seckill.service.impl;

import com.cbt.seckill.dataobject.UserDO;
import com.cbt.seckill.dataobject.UserPasswordDO;
import com.cbt.seckill.error.BusinessException;
import com.cbt.seckill.error.EmBusinessError;
import com.cbt.seckill.mapper.UserMapper;
import com.cbt.seckill.mapper.UserPasswordMapper;
import com.cbt.seckill.service.UserService;
import com.cbt.seckill.service.model.UserModel;
import com.cbt.seckill.utils.RedisStringUtil;
import com.cbt.seckill.validator.ValidatorImpl;
import com.cbt.seckill.validator.ValidatorResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserPasswordMapper userPasswordMapper;

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private RedisStringUtil redisStringUtil;

    @Override
    public UserModel getUserById(Integer id) throws BusinessException {
        UserDO userInfo = userMapper.selectByPrimaryKey(id);
        if (userInfo == null) {
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR);
        }
        UserPasswordDO userPassword = userPasswordMapper.selectByUserId(id);
        return convertFrom(userInfo, userPassword);
    }

    @Override
    @Transactional
    public void register(UserModel model) throws BusinessException {
        if (model == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALID_ERROR);
        }
//        if (StringUtils.isEmpty(model.getName()) || model.getAge() == null
//                || model.getGender() == null || StringUtils.isEmpty(model.getTelphone())) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALID_ERROR);
//        }
        ValidatorResult validate = validator.validate(model);
        if (validate.isHasError()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALID_ERROR, validate.getErrMsg());
        }
        UserDO u = new UserDO();
        u.setTelphone(model.getTelphone());
        List<UserDO> userDOS = userMapper.select(u);
        if (userDOS != null && userDOS.size() > 0) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALID_ERROR, "手机号已注册");
        }

        UserDO userDO = convertFromModel(model);
        userMapper.insertSelectiveUseGeneratedKeys(userDO);

        model.setId(userDO.getId());

        UserPasswordDO passwordDO = convertFromUserModel(model);
        userPasswordMapper.insertSelective(passwordDO);
    }

    @Override
    public UserModel validateLogin(String telphone, String encrptPassword) throws BusinessException {
        UserDO selectDo = new UserDO();
        selectDo.setTelphone(telphone);
        UserDO select = userMapper.selectOne(selectDo);
        if (select == null) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }

        UserPasswordDO userPasswordDO = userPasswordMapper.selectByUserId(select.getId());
        UserModel userModel = convertFrom(select, userPasswordDO);
        if (!StringUtils.equals(encrptPassword, userModel.getEncrptPassword())) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }

//        redisStringUtil.setObject("user::" + userModel.getId(), userModel, 30, TimeUnit.MINUTES);

        return userModel;
    }


    private UserPasswordDO convertFromUserModel(UserModel model) {
        if (model == null) {
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setUserId(model.getId());
        userPasswordDO.setEncrptPassword(model.getEncrptPassword());
        return userPasswordDO;

    }

    private UserDO convertFromModel(UserModel userModel) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);
        return userDO;
    }

    private UserModel convertFrom(UserDO userInfo, UserPasswordDO userPassword) {
        if (userInfo == null) {
            return null;
        }

        UserModel userInfoModel = new UserModel();
        BeanUtils.copyProperties(userInfo, userInfoModel);

        if (userPassword != null) {
            userInfoModel.setEncrptPassword(userPassword.getEncrptPassword());
        }
        return userInfoModel;
    }
}
