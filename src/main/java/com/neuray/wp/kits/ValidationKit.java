////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.kits;

import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * 数据校验工具类,基于JSR 303 标准实现
 * 时间 2018/12/26
 * @author 小听风
 * @version v1.0
 * @see
 * @since
 */
public class ValidationKit {
    private static Validator validator = Validation
            .byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();

    public static <T> void validate(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        // 抛出检验异常
        StringBuilder stringBuilder=new StringBuilder();
        if (constraintViolations.size() > 0) {
            constraintViolations.stream().forEach(c->buildMsg(c,stringBuilder));
            throw new ValidationException(stringBuilder.toString());
        }
    }


    private static String buildMsg(ConstraintViolation constraintViolation,StringBuilder stringBuilder){

        if(stringBuilder.length()==0){
            stringBuilder.append(constraintViolation.getMessage());
        }else {
            stringBuilder.append(",").append(constraintViolation.getMessage());
        }

        return stringBuilder.toString();
    }
}
