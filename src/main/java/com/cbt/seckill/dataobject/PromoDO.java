package com.cbt.seckill.dataobject;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Table(name = "promo")
@Data
public class PromoDO {
    public PromoDO() {
    }

    public PromoDO(Integer itemId) {
        this.itemId = itemId;
    }

    /**
     * id
     */
    @Id
    private Integer id;

    /**
     * 秒杀活动名称
     */
    @Column(name = "promo_name")
    private String promoName;

    /**
     * 秒杀开始时间
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * 秒杀开始时间
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 秒杀商品id
     */
    @Column(name = "item_id")
    private Integer itemId;

    /**
     * 秒杀商品价格
     */
    @Column(name = "promo_item_price")
    private Double promoItemPrice;

}