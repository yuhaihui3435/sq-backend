////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.Dict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictService extends BaseService<Dict> {

    /**
     * 查询所有未删除
     *
     * @return : java.util.List<com.neuray.wp.entity.Dict>
     */
    public List<Dict> listAll() {
        return this.sqlManager.lambdaQuery(Dict.class).andIsNull(Dict::getDeAt).select();
    }

    /**
     * 检查字典名称是否存在
     *
     * @param dName
     * @param id
     * @return
     */
    public List<Dict> checkDName(String dName, Long id) {
        if (id == null)
            return this.sqlManager.lambdaQuery(Dict.class).andEq(Dict::getDictName, dName).andIsNull(Dict::getDeAt).select();
        else
            return this.sqlManager.lambdaQuery(Dict.class).andNotEq(Dict::getId, id).andEq(Dict::getDictName, dName).andIsNull(Dict::getDeAt).select();
    }


}