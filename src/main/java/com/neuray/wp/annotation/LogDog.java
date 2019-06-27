////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.annotation;

import com.neuray.wp.Consts;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogDog {
    String desc() default "";//日志说明
    Consts.LOGTYPE logType() default Consts.LOGTYPE.OTHER;
    Consts.REQSOURCE reqSource()default Consts.REQSOURCE.INNER;
}
