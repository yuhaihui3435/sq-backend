////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity.artice;


import com.neuray.wp.core.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

/*
 * 互动
 * gen by xtf 2019-06-27
 *
 */
@Data
@Builder
@Table(name = "INTERACTIVE_RECORD_T")
public class InteractiveRecord extends BaseEntity {
    @Tolerate
    public InteractiveRecord() {
    }

    @AutoID
    private Long id;

    private Long targetId;

    private String type;

    private Long userLoginId;

    private String targetType;

    private Date crAt;

}