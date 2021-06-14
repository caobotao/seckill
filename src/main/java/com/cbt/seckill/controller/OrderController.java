package com.cbt.seckill.controller;

import com.cbt.seckill.error.BusinessException;
import com.cbt.seckill.error.EmBusinessError;
import com.cbt.seckill.response.CommonReturnType;
import com.cbt.seckill.service.OrderService;
import com.cbt.seckill.service.model.OrderModel;
import com.cbt.seckill.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping("/createorder")
    public CommonReturnType createOrder(@RequestParam Integer itemId,
                                        @RequestParam Integer amount,
                                        @RequestParam Integer promoId) throws BusinessException {
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALID_ERROR, "用户未登录, 不能下单");
        }
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");

        OrderModel orderModel = orderService.createOrder(userModel.getId(), itemId, promoId, amount);
        return CommonReturnType.create(null);
    }
}
