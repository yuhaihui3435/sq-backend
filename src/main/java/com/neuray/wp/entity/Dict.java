////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity;


import com.neuray.wp.core.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/*
 * 数据字典
 * gen by xtf 2019-01-09
 *
 */
@Data
@Builder
@Table(name = "DICT_T")
public class Dict extends BaseEntity {
    @Tolerate
    public Dict() {
    }

    @AutoID
    private Long id;
    //字典值
    @NotBlank(message = "字典值必填")
    private String dictVal;

    //字典名称
    @NotBlank(message = "字典名称必填")
    private String dictName;

    //创建人
    private Long crBy;
    //创建时间
    private Date crAt;
    //更新人
    private Long upBy;
    //更新时间
    private Date upAt;
    //删除人
    private Long deBy;
    //删除时间
    private Date deAt;

}