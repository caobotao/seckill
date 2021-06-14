package com.cbt.seckill.mapper;

import com.cbt.seckill.dataobject.UserDO;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface UserMapper extends Mapper<UserDO>, MySqlMapper<UserDO> {
    int insertSelectiveUseGeneratedKeys(UserDO userDO);
}