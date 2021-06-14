package com.cbt.seckill.dataobject;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "item")
@Data
public class ItemDO {
    /**
     * id
     */
    @Id
    private Integer id;

    /**
     * 商品名称
     */
    private String title;

    /**
     * 商品价格
     */
    private Double price;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品销量
     */
    private Integer sales;

    /**
     * 图片链接
     */
    @Column(name = "img_url")
    private String imgUrl;

}