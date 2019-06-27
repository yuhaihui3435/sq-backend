////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp;

import lombok.Data;

import java.math.BigDecimal;

public interface Consts {

    String jwtKey="8886df7fc3a34e26a61c034d5ec8245d8886df7fc3a34e26a61c034d5ec8245da61c034d5ec8245d8886df7fc3a34e26a61c0";

    BigDecimal BYTES_TO_M=new BigDecimal("1024").multiply(new BigDecimal("1024"));

    /**
     * 启用状态
     */
    enum ENABLESTATUS{
        YES("1"),NO("2");
        private String val;
        ENABLESTATUS(String val){
            this.val=val;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }
    }

    /**
     * 数据状态
     */
    enum STATUS{
        AVAILABLE("00","正常"),DISABLE("01","停用");

        private String code;
        private String val;

        STATUS(String code,String val){
            this.code=code;
            this.val=val;
        }

        public static String getVal(String code){
            for (int i = 0; i < STATUS.values().length; i++) {
                if(STATUS.values()[i].code.equals(code)){
                    return STATUS.values()[i].val;
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }
    }

    /**
     * 字典类型
     */
    enum DICT{
        WIDGETTYPE("widgetType");
        private String key;
        DICT(String key){
            this.key=key;
        }
    }

    enum LOGTYPE{
        LOGIN("登录"),LOGOUT("登出"),QUERY("查询"),SAVE("新增"),UPDATE("修改"),DEL("删除"),LOGICDEL("逻辑删除"),BUSINESS("业务调用"),OTHER("其它");
        private String label;
        LOGTYPE(String label){
            this.label=label;
        }
        public String getLabel(){
            return this.label;
        }
    }

    enum REQSOURCE{
        INNER("内部访问"),OUTER("外部系统访问");
        private String label;
        REQSOURCE(String label){
            this.label=label;
        }

        public String getLabel() {
            return label;
        }
    }
}
