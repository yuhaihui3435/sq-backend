////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp;

import lombok.Data;

import java.math.BigDecimal;

public interface Consts {

    String jwtKey="8886df7fc3a34e26a61c034d5ec8245d8886df7fc3a34e26a61c034d5ec8245da61c034d5ec8245d8886df7fc3a34e26a61c0";
    String PWD_SECURE_KEY = "neuray2503@neuray.cn";
    String MEMBER_LOCKED="member_locked_";
    String MEMBER_LAST_LOGIN="member_last_login_";
    String MEMBER_TRY_COUNT="member_try_count_";
    String CURR_MEMBER="curr_member_";
    Integer MEMBER_LOGIN_TRY_COUNT=3;
    String MEMBER_TOKEN="memberToken";
    String LIST_TPL="list_tpl";
    String DETAIL_TPL="detail_tpl";
    String SYS_CONF_AUTHOR="author";
    String SYS_CONF_CONTENT="content";

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
     * 是否
     */
    enum YESORNO{
        YES("0"),NO("1");
        private String val;
        YESORNO(String val){
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
    enum FILETYPE{
        PIC,FILE,DOC,PDF,EXCEL;
    }

    enum USERLOGIN_TYPE{
        MEMBER("00","会员"),DOCTOR("01","医生");
        private String code;
        private String val;

        USERLOGIN_TYPE(String code,String val){
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
     * 数据状态
     */
    enum LESSON_STATUS{
        READY("00","未开始"),PROCESSING("01","进行中"),FINISHED("02","已完结"),;

        private String code;
        private String val;

        LESSON_STATUS(String code,String val){
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


        public String getVal() {
            return val;
        }

    }
}
