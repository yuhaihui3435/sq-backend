////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity.website;


import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import org.beetl.sql.core.annotatoin.Table;
import com.neuray.wp.core.BaseEntity;
import com.neuray.wp.Consts;

import java.util.*;
import javax.validation.constraints.*;

import org.beetl.sql.core.annotatoin.AssignID;

/*
 * 友情链接
 * gen by xtf 2019-07-12
 *
 */
@Data
@Builder
@Table(name = "LINKS_T")
public class Links extends BaseEntity {
    @Tolerate
    public Links() {
    }

    //说明
    private String describe;

    //链接地址
//    @NotBlank(message = "链接地址必填")
    private String url;

    @AssignID
    private Long id;

    //链接图片
    @NotBlank(message = "链接图片必填")
    private String img;

    //链接名称
    @NotBlank(message = "链接名称必填")
    private String name;

}