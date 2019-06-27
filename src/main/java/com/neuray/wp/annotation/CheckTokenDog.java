////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.annotation;

import java.lang.annotation.*;

/**
 * 检查用户Token
 * 时间 2018/12/26
 * @author 小听风
 * @version v1.0
 * @see
 * @since
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckTokenDog {
    boolean ignore() default false;//是否忽略
}
