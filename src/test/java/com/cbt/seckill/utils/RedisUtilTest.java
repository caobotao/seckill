package com.cbt.seckill.utils;

import com.cbt.seckill.dataobject.UserDO;
import com.cbt.seckill.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtilTest {

    @Autowired
    private RedisStringUtil redisUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void get() {
        UserDO userDO = userMapper.selectByPrimaryKey(19);
        userDO.setName("张三");
        redisUtil.setObject("user::" + userDO.getId(), userDO);
        UserDO redisUserDo = (UserDO) redisUtil.getObject("user::" + userDO.getId(), UserDO.class);
        System.out.println(redisUserDo.toString());
        assertEquals(userDO.toString(), redisUserDo.toString());
    }
}