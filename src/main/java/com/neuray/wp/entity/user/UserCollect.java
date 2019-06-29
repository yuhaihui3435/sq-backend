////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity.user;


import com.neuray.wp.core.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/*
 * userCollect
 * gen by xtf 2019-06-27
 *
 */
@Data
@Builder
@Table(name = "USER_COLLECT_T")
public class UserCollect extends BaseEntity {
    @Tolerate
    public UserCollect() {
    }

    @NotBlank(message = "必填")
    private Long userLoginId;

    @NotBlank(message = "必填")
    private Long targetId;
    @AutoID
    private Long id;

    private Date crAt;

    @NotBlank(message = "必填")
    private String type;

}