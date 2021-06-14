package com.cbt.seckill.service.impl;

import com.cbt.seckill.dataobject.PromoDO;
import com.cbt.seckill.mapper.PromoDOMapper;
import com.cbt.seckill.service.PromoService;
import com.cbt.seckill.service.model.PromoModel;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoDOMapper promoDOMapper;


    @Override
    public PromoModel getPromoByItemId(Integer itemId) {

        PromoDO promoDOSelect = new PromoDO();
        promoDOSelect.setItemId(itemId);
        PromoDO promoDO = promoDOMapper.selectOne(promoDOSelect);
        if (promoDO == null) {
            return null;
        }
        PromoModel promoModel = convertModelFromDO(promoDO);

        if (promoModel.getStartDate().isAfterNow()) {
            promoModel.setStatus(1);
        } else if (promoModel.getEndDate().isBeforeNow()) {
            promoModel.setStatus(3);
        } else {
            promoModel.setStatus(2);
        }

        return promoModel;
    }

    private PromoModel convertModelFromDO(PromoDO promoDO) {
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO, promoModel);
        promoModel.setPromoItemPrice(new BigDecimal(promoDO.getPromoItemPrice()));
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));
        return promoModel;
    }
}
