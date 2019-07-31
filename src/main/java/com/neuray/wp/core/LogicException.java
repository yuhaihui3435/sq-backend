////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.core;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Slf4j
public class LogicException extends RuntimeException {

    protected String errMsg;

    protected String errCode;

    public String getErrMsg() {
        return errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public LogicException(String errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public LogicException(String errMsg) {
        super(errMsg);
        this.errCode = "9999";
        this.errMsg = errMsg;
    }

    public String getPrettyExceptionMsg() {
        return String.format("业务异常:编号>>【%s】,信息>>【%s】", this.errCode, this.errMsg);
    }

    public String getSimpleExceptionMsg(){
        return this.errMsg;
    }
}
