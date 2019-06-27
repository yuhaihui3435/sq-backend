////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity.artice;


import com.neuray.wp.core.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/*
 * 栏目
 * gen by xtf 2019-06-27
 *
 */
@Data
@Builder
@Table(name = "COLUMN_T")
public class Column extends BaseEntity {
    @Tolerate
    public Column() {
    }

    private Long deBy;

    @AssignID
    private Long id;

    private Long crBy;

    private Date upAt;

    private Date deAt;

    private Date crAt;

    private Long upBy;

    @NotBlank(message = "必填")
    private String name;

    private Long parentId;

}