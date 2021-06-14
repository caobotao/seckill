package com.cbt.seckill.service;

import com.cbt.seckill.error.BusinessException;
import com.cbt.seckill.service.model.OrderModel;

public interface OrderService {
    OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException;
}
