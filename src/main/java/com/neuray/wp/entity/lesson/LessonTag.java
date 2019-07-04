////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity.lesson;


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
* 课程标签
* gen by xtf 2019-07-04
*
*/
@Data
@Builder
@Table(name="LESSON_TAG_T")
public class LessonTag extends BaseEntity{
    @Tolerate
    public LessonTag(){}

        @NotBlank(message = "必填")        private Long lessonId ;

        @NotBlank(message = "必填")        private Long tagId ;

                  @AssignID
                  private Long id;

}