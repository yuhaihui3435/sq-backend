////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity.comment;


import com.neuray.wp.core.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

/*
 * 评论
 * gen by xtf 2019-06-27
 *
 */
@Data
@Builder
@Table(name = "COMMENT_T")
public class Comment extends BaseEntity {
    @Tolerate
    public Comment() {
    }

    private Long commentBy;

    @AutoID
    private Long id;

    private Long targetId;

    private String content;

    private Date commentAt;

    private Long parentId;

    private String anonymous;

    private String type;

}