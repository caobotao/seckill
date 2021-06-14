package com.cbt.seckill.mapper;

import com.cbt.seckill.dataobject.UserDO;
import com.cbt.seckill.dataobject.UserPasswordDO;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface UserPasswordMapper extends Mapper<UserPasswordDO>, MySqlMapper<UserPasswordDO> {
    UserPasswordDO selectByUserId(Integer userId);

}