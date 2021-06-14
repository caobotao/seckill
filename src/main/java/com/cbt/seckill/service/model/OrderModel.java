package com.cbt.seckill.service.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderModel {
    private String id;
    private Integer userId;
    private Integer itemId;
    //秒杀商品id, 不为null则是秒杀商品
    private Integer promoId;
    //promoId不为null则是秒杀价格
    private BigDecimal itemPrice;
    private Integer amount;
    //promoId不为null则是秒杀价格
    private BigDecimal orderPrice;
}
