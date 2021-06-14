package com.cbt.seckill.mapper;

import com.cbt.seckill.dataobject.ItemDO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ItemDOMapper extends Mapper<ItemDO>, MySqlMapper<ItemDO>, IdsMapper<ItemDO>, ConditionMapper<ItemDO> {
    int insertSelectiveUseGeneratedKeys(ItemDO itemDO);

    void increaseSales(@Param("id") Integer id, @Param("amount") Integer amount);
}