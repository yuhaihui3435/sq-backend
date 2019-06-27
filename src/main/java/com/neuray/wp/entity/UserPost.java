////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity;


import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;
import com.neuray.wp.core.BaseEntity;
import com.neuray.wp.Consts;
import java.util.*;
import javax.validation.constraints.*;
/*
* 用户职位
* gen by xtf 2019-01-25
*
*/
@Data
@Builder
@Table(name="USER_POST_T")
public class UserPost extends BaseEntity{
    @Tolerate
    public UserPost(){}

        private Date deAt ;

        private Long crBy ;

        private Long postId ;

        private Long upBy ;

        private Long deBy ;

        private Date upAt ;

        private Long sysUserId ;

        private Date crAt ;

    @AssignID
                  private Long id;

}