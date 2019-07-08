////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2019. 东睿科技有限公司.保留所有权利
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.handler.exception;

import lombok.Data;

/**
 * @author:小听风 创建时间:2019/7/8
 * 版本:v1.0
 * @Description:
 */
@Data
public class ErrorInfo {
    private String time;//发生时间
    private String url;//访问Url
    private String error;//错误类型
    String stackTrace;//错误的堆栈轨迹
    private int statusCode;//状态码
    private String reasonPhrase;//状态码
}
