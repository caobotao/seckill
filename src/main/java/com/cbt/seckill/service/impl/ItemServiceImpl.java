package com.cbt.seckill.service.impl;

import com.cbt.seckill.dataobject.ItemDO;
import com.cbt.seckill.dataobject.ItemStockDO;
import com.cbt.seckill.dataobject.PromoDO;
import com.cbt.seckill.error.BusinessException;
import com.cbt.seckill.error.EmBusinessError;
import com.cbt.seckill.mapper.ItemDOMapper;
import com.cbt.seckill.mapper.ItemStockDOMapper;
import com.cbt.seckill.mapper.PromoDOMapper;
import com.cbt.seckill.service.ItemService;
import com.cbt.seckill.service.PromoService;
import com.cbt.seckill.service.model.ItemModel;
import com.cbt.seckill.service.model.PromoModel;
import com.cbt.seckill.validator.ValidatorImpl;
import com.cbt.seckill.validator.ValidatorResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private ItemStockDOMapper itemStockDOMapper;

    @Autowired
    private PromoService promoService;

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        ValidatorResult result = validator.validate(itemModel);
        if (result.isHasError()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALID_ERROR, result.getErrMsg());
        }
        ItemDO itemDO = convertFromItemModel(itemModel);
//        String sql = sqlSessionFactory.getConfiguration().getMappedStatement
//                ("com.cbt.seckill.mapper.ItemDOMapper.insertSelectiveUseGeneratedKeys")
//                .getBoundSql(itemDO).getSql();

        itemDOMapper.insertSelectiveUseGeneratedKeys(itemDO);
        itemModel.setId(itemDO.getId());

        ItemStockDO itemStockDO = convertItemStockFromItemModel(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);
        return this.getItemById(itemModel.getId());
    }

    private ItemStockDO convertItemStockFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }

    private ItemDO convertFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel, itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }

    @Override
    public List<ItemModel> listItem() {
        Condition condition = new Condition(ItemDO.class);
        condition.orderBy("sales").desc();
        List<ItemDO> itemDOs = itemDOMapper.selectByCondition(condition);
        List<ItemModel> itemModels = itemDOs.stream().map(itemDO -> {
            ItemStockDO select = new ItemStockDO();
            select.setItemId(itemDO.getId());
            ItemStockDO itemStockDO = itemStockDOMapper.selectOne(select);
            ItemModel itemModel = convertModelFromDataobject(itemDO, itemStockDO);
            return itemModel;
        }).collect(Collectors.toList());
        return itemModels;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
//        List<ItemDO> itemDOS = itemDOMapper.selectByIds("3,4");
        if (itemDO == null) {
            return null;
        }
        ItemStockDO itemStockDO = getItemStockByItemId(id);

        ItemModel itemModel = convertModelFromDataobject(itemDO, itemStockDO);

        PromoModel promoModel = promoService.getPromoByItemId(id);
        if (promoModel != null && promoModel.getStatus() != 3) {
            itemModel.setPromoModel(promoModel);
        }

        return itemModel;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) {
        int result = itemStockDOMapper.decreaseStock(itemId, amount);
        return result > 0;
    }

    @Override
    public void increaseSales(Integer itemId, Integer amount) {
        itemDOMapper.increaseSales(itemId, amount);
    }

    private ItemModel convertModelFromDataobject(ItemDO itemDO, ItemStockDO itemStockDO) {
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO, itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());
        return itemModel;
    }

    public ItemStockDO getItemStockByItemId(Integer itemId) {
        ItemStockDO selected = new ItemStockDO();
        selected.setItemId(itemId);
        List<ItemStockDO> select = itemStockDOMapper.select(selected);
        if (select != null && select.size() > 0) {
            return select.get(0);
        }
        return null;
    }
}
