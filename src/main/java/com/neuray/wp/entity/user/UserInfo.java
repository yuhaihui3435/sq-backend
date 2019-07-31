////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity.user;


import com.neuray.wp.core.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/*
 * 用户详细信息
 * gen by xtf 2019-07-01
 *
 */
@Data
@Builder
@Table(name = "USER_INFO_T")
public class UserInfo extends BaseEntity {
    @Tolerate
    public UserInfo() {
    }

    private String realname;

    //学历-00:初中,01:高中,02:中专,03:大专,04:本科,05:研究生,06:硕士,07:博士,08:博士后,09:其他
    private String education;

    private String province;

    //用户类型-00:普通用户,01:高级用户
    private String type;

    private Date birthday;

    //登录ID
    private Long loginId;

    @AssignID
    private Long id;

    private String address;

    //性别-00:男,01:女
    @NotBlank(message = "性别必填")
    private String sex;

    public String getSexStr() {
        String ret = null;
        return ret;
    }

    //头像
    private String avatar;

    //行业
    private Long career;

    @NotBlank(message = "昵称必填")
    private String nickname;

    private String city;

    private String area;

    private UserLogin userLogin;

}