////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.core;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * JSON返回数据容器
 */
@Builder
@Data
public class RespBody<T> {

    /**
     * JSON返回码：成功
     */
    public static final int SUCCESS = 1000;
    /**
     * JSON返回码：业务错误
     */
    public static final int BUSINESS_ERROR = 1001;
    /**
     * JSON返回码：系统错误
     */
    public static final int SYS_ERROR = 1002;

    /**
     * 返回消息
     */
    private String msg;

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回内容
     */
    private T body = null;

    /**
     * JSON返回数据容器（默认返回成功状态码）
     */
    @Tolerate
    public RespBody() {

        this.code = SUCCESS;
        this.msg = "";
    }

    public static RespBody error(){
        return RespBody.builder().code(BUSINESS_ERROR).msg("操作失败").build();
    }

    public static RespBody error(String msg){
        return RespBody.builder().code(BUSINESS_ERROR).msg(msg).build();
    }
    public static RespBody error(String msg,Object body){
        return RespBody.builder().code(BUSINESS_ERROR).msg(msg).body(body).build();
    }

    public static RespBody success(String msg,Object body){
        return RespBody.builder().code(SUCCESS).msg(msg).body(body).build();
    }
    public static RespBody success(String msg){
        return RespBody.builder().code(SUCCESS).msg(msg).build();
    }
    public static RespBody success(){
        return RespBody.builder().code(SUCCESS).msg("操作成功").build();
    }
}
