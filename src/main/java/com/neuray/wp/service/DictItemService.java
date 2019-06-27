////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.DictItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictItemService extends BaseService<DictItem> {
    /**
     * 检查条目名称是否存在
     *
     * @param itemName
     * @param id
     * @return
     */
    public List<DictItem> checkItemName(String itemName, Long id) {
        if (id == null)
            return this.sqlManager.lambdaQuery(DictItem.class).andEq(DictItem::getItemName, itemName).andIsNull(DictItem::getDeAt).select();
        else
            return this.sqlManager.lambdaQuery(DictItem.class).andNotEq(DictItem::getId, id).andEq(DictItem::getItemName, itemName).andIsNull(DictItem::getDeAt).select();
    }

    /**
     * 检查条目值是否存在
     *
     * @param itemVal
     * @param id
     * @return
     */
    public List<DictItem> checkItemVal(String itemVal, Long id) {
        if (id == null)
            return this.sqlManager.lambdaQuery(DictItem.class).andEq(DictItem::getItemVal, itemVal).andIsNull(DictItem::getDeAt).select();
        else
            return this.sqlManager.lambdaQuery(DictItem.class).andNotEq(DictItem::getId, id).andEq(DictItem::getItemVal, itemVal).andIsNull(DictItem::getDeAt).select();
    }



}