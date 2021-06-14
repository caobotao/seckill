package com.cbt.seckill.service.model;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;

@Data
public class PromoModel {
    private Integer id;
    private String promoName;
    private DateTime startDate;
    private DateTime endDate;
    private Integer itemId;
    private BigDecimal promoItemPrice;

    // 秒杀状态 1-未开始 2-正在进行 3-已结束
    private Integer status;
}
