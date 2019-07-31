////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2019. 东睿科技有限公司.保留所有权利
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.handler.exception;

import com.fasterxml.classmate.util.ResolvedTypeKey;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.exception.AuthorizationException;
import com.neuray.wp.exception.LoginException;
import com.neuray.wp.kits.ReqKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    private final static String DEFAULT_ERROR_VIEW = "error";//错误信息页

    @Autowired
    private ErrorInfoBuilder errorInfoBuilder;//错误信息的构建工具
    @Value("${spring.servlet.multipart.max-file-size}")
    private String max_file_size;
    @Value("${spring.servlet.multipart.max-request-size}")
    private String max_request_size;

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Object handleMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException error) {
        log.error("缺少请求参数 ${}", error.getMessage());
        if(ReqKit.isAjaxRequest(request)) {
            return  RespBody.error("缺少请求参数>>" + error.getMessage());
        }

        return new ModelAndView(DEFAULT_ERROR_VIEW, "errorInfo", errorInfoBuilder.getErrorInfo(request, error));
    }

    /**
     * 处理所有业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(LogicException.class)
    @ResponseBody
    public Object handleBusinessException(HttpServletRequest request,LogicException e){
        log.error("系统业务操作失败：异常编号 {} 异常信息 {}",e.getErrCode(),e.getErrMsg());
        if(ReqKit.isAjaxRequest(request)) {

            return  RespBody.error(e.getSimpleExceptionMsg());
        }
        return new ModelAndView(DEFAULT_ERROR_VIEW, "errorInfo", errorInfoBuilder.getErrorInfo(request, e));
    }

    /**
     * 处理所有接口数据验证异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Object handleMethodArgumentNotValidException(HttpServletRequest request,MethodArgumentNotValidException e){
        log.error("数据不合法：异常信息>>{}",e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        String errorMesssage = "请求参数不合法:\n";
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage += fieldError.getDefaultMessage() + "\n";
        }
        log.error("数据校验失败原因:"+errorMesssage);
        if(ReqKit.isAjaxRequest(request)) {
            return  RespBody.error(errorMesssage);
        }
        return new ModelAndView(DEFAULT_ERROR_VIEW, "errorInfo", errorInfoBuilder.getErrorInfo(request, e));
    }

    @ExceptionHandler(value =BindException.class)
    @ResponseBody
    public Object handleBindException(HttpServletRequest request,BindException e)  {
        log.error("数据绑定失败：异常信息>>{}",e.getMessage());
        FieldError fieldError = e.getFieldError();
        StringBuilder sb = new StringBuilder();
        sb.append(fieldError.getField()).append("=[").append(fieldError.getRejectedValue()).append("]")
                .append(fieldError.getDefaultMessage());
        if(ReqKit.isAjaxRequest(request)) {
            return  RespBody.error(sb.toString());
        }
        return new ModelAndView(DEFAULT_ERROR_VIEW, "errorInfo", errorInfoBuilder.getErrorInfo(request, e));
    }


    @ExceptionHandler(value =ValidationException.class)
    @ResponseBody
    public Object handleValidationException(HttpServletRequest request,ValidationException e) {
        log.error("数据校验：校验未通过，校验结果为>>{}",e.getMessage());
        if(ReqKit.isAjaxRequest(request)) {
            return  RespBody.error(e.getMessage());
        }
        return new ModelAndView(DEFAULT_ERROR_VIEW, "errorInfo", errorInfoBuilder.getErrorInfo(request, e));
    }

    /**
     * 处理所有不可知的异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(HttpServletRequest request,Exception e){
        log.error("系统错误：异常信息为>>{}",e.getMessage());
        e.printStackTrace();
        if(ReqKit.isAjaxRequest(request)) {
            return  RespBody.error("系统未知错误了，请联系管理员！");
        }
        return new ModelAndView(DEFAULT_ERROR_VIEW, "errorInfo", errorInfoBuilder.getErrorInfo(request, e));
    }

    /**
     * 上传文件过大
     * @param e
     * @return
     */
    @ExceptionHandler(value =MultipartException.class)
    @ResponseBody
    public Object handleMultipartException(HttpServletRequest request,MultipartException e){
        log.error("上传文件过大");
        if(ReqKit.isAjaxRequest(request)) {
            return  RespBody.error("上传的文件过大，单个文件不要超过" + max_file_size + ",一次上传不能超过" + max_request_size);
        }
        return new ModelAndView(DEFAULT_ERROR_VIEW, "errorInfo", errorInfoBuilder.getErrorInfo(request, e));
    }
    @ExceptionHandler(value =LoginException.class)
    public Object handlerLoginException(HttpServletRequest request,HttpServletResponse response,LoginException e){
        log.error("未登录，请重新登录");
        if(ReqKit.isAjaxRequest(request)) {
            response.setStatus(401);
            return  RespBody.error("身份失效，请重新登陆");

        }
        return new ModelAndView(DEFAULT_ERROR_VIEW, "errorInfo", errorInfoBuilder.getErrorInfo(request, e));
    }

    @ExceptionHandler(value =AuthorizationException.class)
    public Object handlerLoginException(HttpServletRequest request,HttpServletResponse response,AuthorizationException authorizationExcetpion){
        log.error("没有访问权限");
        if(ReqKit.isAjaxRequest(request)) {
            response.setStatus(403);
            return  RespBody.error("您没有访问权限");
        }
        return new ModelAndView(DEFAULT_ERROR_VIEW, "errorInfo", errorInfoBuilder.getErrorInfo(request, authorizationExcetpion));
    }
}
