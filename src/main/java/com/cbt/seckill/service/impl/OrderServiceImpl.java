package com.cbt.seckill.service.impl;

import com.cbt.seckill.dataobject.OrderDO;
import com.cbt.seckill.dataobject.SequenceDO;
import com.cbt.seckill.error.BusinessException;
import com.cbt.seckill.error.EmBusinessError;
import com.cbt.seckill.mapper.OrderDOMapper;
import com.cbt.seckill.mapper.SequenceDOMapper;
import com.cbt.seckill.service.ItemService;
import com.cbt.seckill.service.OrderService;
import com.cbt.seckill.service.PromoService;
import com.cbt.seckill.service.UserService;
import com.cbt.seckill.service.model.ItemModel;
import com.cbt.seckill.service.model.OrderModel;
import com.cbt.seckill.service.model.PromoModel;
import com.cbt.seckill.service.model.UserModel;
import com.cbt.seckill.utils.RedisStringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Autowired
    private PromoService promoService;
//
//    @Autowired
//    private RedisStringUtil redisStringUtil;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException {
        // 入参校验
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALID_ERROR, "商品信息不存在");
        }
        UserModel userModel = userService.getUserById(userId);
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALID_ERROR, "用户信息不存在");
        }

        if (amount <= 0 && amount >= 99) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALID_ERROR, "数量信息不存在");
        }
        // 落单减库存
        boolean result = itemService.decreaseStock(itemId, amount);
        if (!result) {
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }
        if (promoId != null) {
            if (promoId.intValue() != itemModel.getPromoModel().getId()) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALID_ERROR, "活动信息不正确");
            } else if (itemModel.getPromoModel().getStatus() != 2) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALID_ERROR, "活动还未开始");
            }
        }
        //订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setItemId(itemId);
        orderModel.setUserId(userId);
        orderModel.setAmount(amount);
        if (promoId != null) {
            orderModel.setPromoId(promoId);
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        } else {
            orderModel.setItemPrice(itemModel.getPrice());
        }
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));

        orderModel.setId(generateOrderNo());
        OrderDO orderDO = convertDOFromModel(orderModel);
        orderDOMapper.insertSelective(orderDO);

        //增加销量
        itemService.increaseSales(itemId, amount);

        return orderModel;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private String generateOrderNo() {
        StringBuilder sb = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        sb.append(nowDate);
        SequenceDO sequenceDO = sequenceDOMapper.selectByName("order_info");
        int currentValue = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(currentValue + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        String formatCurrent = String.format("%06d", currentValue);
        sb.append(formatCurrent);
        sb.append("00");
        return sb.toString();
    }

    private OrderDO convertDOFromModel(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }

        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());

        return orderDO;
    }
}
