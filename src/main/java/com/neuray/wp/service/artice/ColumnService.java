////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service.artice;

import cn.hutool.core.util.StrUtil;
import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.artice.Column;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ColumnService extends BaseService<Column> {
    /**
     * 检查是否存在
     *
     * @param name
     * @param id
     * @return
     */
    public List<Column> checkName(String name, Long id) {
        if (id == null) {
            if (StrUtil.isNotBlank(name)) {
                return this.sqlManager.lambdaQuery(Column.class).andEq(Column::getName, name).andIsNull(Column::getDeAt).select();
            }
        } else {
            if (StrUtil.isNotBlank(name)) {
                return this.sqlManager.lambdaQuery(Column.class).andNotEq(Column::getId, id).andEq(Column::getName, name).andIsNull(Column::getDeAt).select();
            }
        }
        return null;
    }

    /**
     * 递归查询
     * @param columnList
     */
    public void recursive(List<Column> columnList){
        columnList.stream().forEach(column -> {
            if(column.getParentId()!=null) {
                column.setParent(one(column.getParentId()));
            }
            List<Column> tmp=many("artice.column.sample",Column.builder().parentId(column.getId()).build());
            if(!tmp.isEmpty()) {
                recursive(tmp);
                column.setChildren(tmp);
            }

        });
    }


}