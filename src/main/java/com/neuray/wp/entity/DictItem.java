////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity;


import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;
import com.neuray.wp.core.BaseEntity;

import java.util.*;
import javax.validation.constraints.*;

/*
 * 字典条目
 * gen by xtf 2019-01-11
 *
 */
@Data
@Builder
@Table(name = "DICT_ITEM_T")
public class DictItem extends BaseEntity {
    @Tolerate
    public DictItem() {
    }

    @AssignID
    private Long id;
    //删除人
    private Long deBy;

    //条目值
    @NotBlank(message = "条目值必填")
    private String itemVal;

    //条目名称
    @NotBlank(message = "条目名称必填")
    private String itemName;

    //字典
    @NotNull(message = "字典必填")
    private Long dictId;

    //创建时间
    private Date crAt;

    //创建人
    private Long crBy;

    //更新时间
    private Date upAt;

    //删除时间
    private Date deAt;

    //更新人
    private Long upBy;

}