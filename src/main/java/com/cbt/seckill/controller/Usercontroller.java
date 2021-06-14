package com.cbt.seckill.controller;

import com.cbt.seckill.controller.viewobject.UserInfoVo;
import com.cbt.seckill.error.BusinessException;
import com.cbt.seckill.error.EmBusinessError;
import com.cbt.seckill.response.CommonReturnType;
import com.cbt.seckill.service.UserService;
import com.cbt.seckill.service.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

import static com.alibaba.druid.util.Utils.md5;

@RestController
@RequestMapping("/user")
@Slf4j
public class Usercontroller extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @GetMapping("/get")
    public CommonReturnType getUserById(@RequestParam Integer id) throws BusinessException {
        UserModel userInfoModel = userService.getUserById(id);

        UserInfoVo vo = convertFrom(userInfoModel);
        return CommonReturnType.create(vo);
    }

    @PostMapping("/register")
    public CommonReturnType register(@RequestParam String telphone,
                                     @RequestParam String otpCode,
                                     @RequestParam String name,
                                     @RequestParam String password,
                                     @RequestParam Integer gender,
                                     @RequestParam Integer age) throws BusinessException {
        // 验证手机号喝验证码是否匹配
        String optInSession = (String) this.httpServletRequest.getSession().getAttribute(telphone);

        if (!StringUtils.equals(otpCode, optInSession)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALID_ERROR, "短信验证码不符合");
        }

        UserModel userModel = new UserModel();
        userModel.setTelphone(telphone);
        userModel.setName(name);
        userModel.setGender(gender.byteValue());
        userModel.setAge(age);
        userModel.setEncrptPassword(password);
        userModel.setRegisterMode("byphone");
        userModel.setEncrptPassword(md5(password));

        userService.register(userModel);

        return CommonReturnType.create(null);
    }


    @PostMapping("/login")
    public CommonReturnType login(@RequestParam String telphone, @RequestParam String password) throws BusinessException {
        if (StringUtils.isEmpty(telphone) || StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALID_ERROR);
        }
        UserModel userModel = userService.validateLogin(telphone, md5(password));

        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);

        return CommonReturnType.create(null);
    }


    @PostMapping("/getotp")
    public CommonReturnType getOpt(@RequestParam String telphone) {
        Random r = new Random();
        int ranNum = r.nextInt(899999);
        ranNum += 100000;
        String opt = String.valueOf(ranNum);

        httpServletRequest.getSession().setAttribute(telphone, opt);
        log.info("telphone:{},opt:{}", telphone, opt);

//        System.out.println("telphone:" + telphone + ",opt:" + opt);

        return CommonReturnType.create(null);
    }

    private UserInfoVo convertFrom(UserModel userInfoModel) {
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfoModel, userInfoVo);
        return userInfoVo;
    }


}
