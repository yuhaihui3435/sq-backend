////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity.user;


import com.neuray.wp.Consts;
import com.neuray.wp.core.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/*
 * 用户登录信息
 * gen by xtf 2019-06-27
 *
 */
@Data
@Builder
@Table(name = "USER_LOGIN_T")
public class UserLogin extends BaseEntity {
    @Tolerate
    public UserLogin() {
    }

    @AutoID
    private Long id;

    private String wxOpenId;


    private String pwd;

    private Date upAt;

    @NotBlank(message = "手机号必填")
    @Pattern(regexp = "^$|^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$", message = "手机号码不正确")
    private String phone;

    private String email;

    private String qqOpenId;

    private String account;

    private String status;

    public String getStatusStr() {
        return Consts.STATUS.getVal(this.status);
    }

    private Long upBy;

    private Date deAt;

    private Date crAt;

    private Long deBy;

    private String type;

}