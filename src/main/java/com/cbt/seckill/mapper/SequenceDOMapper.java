package com.cbt.seckill.mapper;

import com.cbt.seckill.dataobject.SequenceDO;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface SequenceDOMapper extends Mapper<SequenceDO>, MySqlMapper<SequenceDO> {
    SequenceDO selectByName(String name);
}