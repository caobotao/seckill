package com.cbt.seckill.mapper;

import com.cbt.seckill.dataobject.Test;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface TestMapper extends Mapper<Test>, MySqlMapper<Test> {
}