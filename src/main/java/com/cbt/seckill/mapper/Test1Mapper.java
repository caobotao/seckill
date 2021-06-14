package com.cbt.seckill.mapper;

import com.cbt.seckill.dataobject.Test1;

import java.util.List;

public interface Test1Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Test1 record);

    Test1 selectByPrimaryKey(Integer id);

    List<Test1> selectAll();

    int updateByPrimaryKey(Test1 record);
}