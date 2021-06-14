package com.cbt.seckill.dataobject;

import lombok.Data;

import javax.persistence.*;

@Table(name = "item_stock")
@Data
public class ItemStockDO {
    /**
     * id
     */
    @Id
    private Integer id;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 商品id
     */
    @Column(name = "item_id")
    private Integer itemId;

}