////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity;


import com.neuray.wp.Consts;
import com.neuray.wp.core.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/*
 * 职位
 * gen by xtf 2019-01-10
 *
 */
@Data
@Builder
@Table(name = "POST_T")
public class Post extends BaseEntity {
    @Tolerate
    public Post() {
    }

    //创建时间
    private Date crAt;

    //上级ID
    private Long parentId;

    //职位编码
    @NotBlank(message = "职位编码必填")
    @Pattern(regexp = "^$|[A-Za-z0-9_\\-]+", message = "职位编码必须是字母或者数字")
    private String postCode;

    //删除人
    private Long deBy;

    //删除时间
    private Date deAt;

    //更新时间
    private Date upAt;

    //职位名称
    @NotBlank(message = "职位名称必填")
    private String postName;

    @AssignID
    private Long id;

    //状态-00:正常:01停用
    @NotBlank(message = "状态必填")
    private String status;

    public String getStatusStr() {
        String ret = Consts.STATUS.getVal(this.status);
        return ret;
    }

    //更新人
    private Long upBy;

    //创建人
    private Long crBy;


}