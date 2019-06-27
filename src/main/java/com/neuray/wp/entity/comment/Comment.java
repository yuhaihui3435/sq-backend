////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity.comment;


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
* 评论
* gen by xtf 2019-06-27
*
*/
@Data
@Builder
@Table(name="COMMENT_T")
public class Comment extends BaseEntity{
    @Tolerate
    public Comment(){}

        private Long commentBy ;

                  @AssignID
                  private Long id;

        private Long targetId ;

        private String content ;

        private Date commentAt ;

        private Long parentId ;

        private String anonymous ;

        private String type ;

}