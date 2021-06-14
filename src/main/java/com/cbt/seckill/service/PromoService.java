package com.cbt.seckill.service;

import com.cbt.seckill.service.model.PromoModel;

public interface PromoService {
    PromoModel getPromoByItemId(Integer itemId);
}
