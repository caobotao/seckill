package com.cbt.seckill.controller;

import com.cbt.seckill.controller.viewobject.ItemVO;
import com.cbt.seckill.error.BusinessException;
import com.cbt.seckill.response.CommonReturnType;
import com.cbt.seckill.service.ItemService;
import com.cbt.seckill.service.model.ItemModel;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/item")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/create")
    public CommonReturnType createItem(@RequestParam String title,
                                       @RequestParam String description,
                                       @RequestParam BigDecimal price,
                                       @RequestParam Integer stock,
                                       @RequestParam String imgUrl) throws BusinessException {
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);
        ItemModel item = itemService.createItem(itemModel);

        ItemVO itemVO = convertItemVo(item);
        return CommonReturnType.create(itemVO);
    }

    @GetMapping("/get")
    public CommonReturnType getItemById(@RequestParam Integer id) {
        ItemModel itemModel = itemService.getItemById(id);
        ItemVO itemVO = convertItemVo(itemModel);
        return CommonReturnType.create(itemVO);
    }

    @GetMapping("/list")
    public CommonReturnType listItem() {
        List<ItemModel> itemModels = itemService.listItem();
        List<ItemVO> itemVOs = itemModels.stream().map(itemModel -> {
            ItemVO itemVO = convertItemVo(itemModel);
            return itemVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(itemVOs);
    }

    private ItemVO convertItemVo(ItemModel item) {
        if (item == null) {
            return null;
        }
        ItemVO itemVo = new ItemVO();
        BeanUtils.copyProperties(item, itemVo);
        if (item.getPromoModel() != null) {
            itemVo.setPromoStatus(item.getPromoModel().getStatus());
            itemVo.setPromoId(item.getPromoModel().getId());
            itemVo.setStartDate(item.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVo.setPromoPrice(item.getPromoModel().getPromoItemPrice());
        } else {
            itemVo.setPromoStatus(0);
        }


        return itemVo;
    }
}
