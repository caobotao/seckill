package com.cbt.seckill.dataobject;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "order_info")
public class OrderDO {
    /**
     * id
     */
    @Id
    private String id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 商品id
     */
    @Column(name = "item_id")
    private Integer itemId;
    /**
     * 活动id
     */
    @Column(name = "promo_id")
    private Integer promoId;

    /**
     * 商品价格
     */
    @Column(name = "item_price")
    private Double itemPrice;

    /**
     * 购买数量
     */
    private Integer amount;

    /**
     * 订单总金额
     */
    @Column(name = "order_price")
    private Double orderPrice;

}