package com.cbt.seckill.mapper;

import com.cbt.seckill.dataobject.OrderDO;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface OrderDOMapper extends Mapper<OrderDO>, MySqlMapper<OrderDO> {
}