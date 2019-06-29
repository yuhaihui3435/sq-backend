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
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import javax.validation.constraints.NotBlank;

/*
 * 文章相关统计
 * gen by xtf 2019-06-27
 *
 */
@Data
@Builder
@Table(name = "ARTICE_RELATED_COUNT_T")
public class ArticeRelatedCount extends BaseEntity {
    @Tolerate
    public ArticeRelatedCount() {
    }

    @AutoID
    private Long id;

    @NotBlank(message = "必填")
    private Long articeId;

    @NotBlank(message = "必填")
    private Integer version;

    @NotBlank(message = "必填")
    private String type;

    @NotBlank(message = "必填")
    private Long val;

}