package com.cbt.seckill.service.impl;

import com.cbt.seckill.service.ItemService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceImplTest extends TestCase {

    @Autowired
    private ItemService itemService;

    @Test
    public void testDecreaseStock() {
        itemService.decreaseStock(3, 5);
    }
}