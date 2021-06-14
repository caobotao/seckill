package com.cbt.seckill.service.impl;

import com.cbt.seckill.service.OrderService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest extends TestCase {
    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void testGenerateOrderNo() {
//        String s = orderService.generateOrderNo();
//        System.out.println(s);
//        assertEquals(s.length(), 16);
    }
}