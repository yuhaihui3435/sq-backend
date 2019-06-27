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

/*
 * 栏目标签关系
 * gen by xtf 2019-06-27
 *
 */
@Data
@Builder
@Table(name = "COLUMN_TAG_T")
public class ColumnTag extends BaseEntity {
    @Tolerate
    public ColumnTag() {
    }

    @AssignID
    private Long id;

    @NotBlank(message = "必填")
    private Long columnId;

    @NotBlank(message = "必填")
    private Long tagId;

}