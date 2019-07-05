////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.handler;

import com.neuray.wp.core.LogicException;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.exception.AuthorizationException;
import com.neuray.wp.exception.LoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;


/**
 * 全局异常处理
 * 时间 2018/12/26
 * @author 小听风
 * @version v1.0
 * @see
 * @since
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @Value("${spring.servlet.multipart.max-file-size}")
    private String max_file_size;
    @Value("${spring.servlet.multipart.max-request-size}")
    private String max_request_size;

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public RespBody handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("缺少请求参数 ${}", e);
        return  new RespBody("缺少请求参数>>"+e.getMessage(),RespBody.BUSINESS_ERROR);
    }

    /**
     * 处理所有业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(LogicException.class)
    @ResponseBody
    public RespBody handleBusinessException(LogicException e){
        log.error("系统业务操作失败：异常编号 {} 异常信息 {}",e.getErrCode(),e.getErrMsg());
        return new RespBody(e.getPrettyExceptionMsg(),RespBody.SYS_ERROR);
    }

    /**
     * 处理所有接口数据验证异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public RespBody handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("数据不合法：异常信息>>{}",e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        String errorMesssage = "请求参数不合法:\n";
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage += fieldError.getDefaultMessage() + "\n";
        }
        log.error("数据校验失败原因:"+errorMesssage);

        return new RespBody(errorMesssage,RespBody.SYS_ERROR);
    }

    @ExceptionHandler(value =BindException.class)
    @ResponseBody
    public RespBody handleBindException(BindException e)  {
        log.error("数据绑定失败：异常信息>>{}",e.getMessage());
        FieldError fieldError = e.getFieldError();
        StringBuilder sb = new StringBuilder();
        sb.append(fieldError.getField()).append("=[").append(fieldError.getRejectedValue()).append("]")
                .append(fieldError.getDefaultMessage());
        return new RespBody(sb.toString(),RespBody.SYS_ERROR);
    }


    @ExceptionHandler(value =ValidationException.class)
    @ResponseBody
    public RespBody handleValidationException(ValidationException e) {
        log.error("数据校验：校验未通过，校验结果为>>{}",e.getMessage());
        return new RespBody(e.getMessage(),RespBody.SYS_ERROR);
    }

    /**
     * 处理所有不可知的异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RespBody handleException(Exception e){
        log.error("系统错误：异常信息为>>{}",e.getMessage());
        e.printStackTrace();
        return new RespBody("系统未知错误了，请联系管理员！",RespBody.SYS_ERROR);
    }

    /**
     * 上传文件过大
     * @param multipartException
     * @return
     */
    @ExceptionHandler(value =MultipartException.class)
    @ResponseBody
    public RespBody handleMultipartException(MultipartException multipartException){
        log.error("上传文件过大");
        return new RespBody("上传的文件过大，单个文件不要超过"+max_file_size+",一次上传不能超过"+max_request_size,RespBody.SYS_ERROR);
    }
    @ExceptionHandler(value =LoginException.class)
    public void handlerLoginException(HttpServletResponse response,LoginException loginExcetpion){
        log.error("未登录，请重新登录");
        response.setStatus(401);
        return ;
    }

    @ExceptionHandler(value =AuthorizationException.class)
    public void handlerLoginException(HttpServletResponse response,AuthorizationException authorizationExcetpion){
        log.error("没有访问权限");
        response.setStatus(403);
        return ;
    }
}
