package com.cbt.seckill.mapper;

import com.cbt.seckill.dataobject.ItemStockDO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ItemStockDOMapper extends Mapper<ItemStockDO>, MySqlMapper<ItemStockDO>, ConditionMapper<ItemStockDO> {
    int decreaseStock(@Param("itemId") Integer itemId, @Param("amount") Integer amount);
}