////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity.qa;


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
* 问答
* gen by xtf 2019-06-27
*
*/
@Data
@Builder
@Table(name="QA_T")
public class Qa extends BaseEntity{
    @Tolerate
    public Qa(){}

        @NotBlank(message = "必填")        private Long authorId ;

        @NotBlank(message = "必填")        private String title ;

        @NotBlank(message = "必填")        private String content ;

        private String reportStatus ;

        private String anonymous ;

                  @AssignID
                  private Long id;

        private Date crAt ;

}