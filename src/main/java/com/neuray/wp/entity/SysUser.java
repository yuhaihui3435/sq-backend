////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity;


import com.neuray.wp.Consts;
import com.neuray.wp.core.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;
import org.springframework.data.annotation.Transient;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/*
 * 系统用户
 * gen by xtf 2019-01-10
 *
 */
@Data
@Builder
@Table(name = "SYS_USER_T")
public class SysUser extends BaseEntity {
    @Tolerate
    public SysUser() {
    }

    //创建时间
    private Date crAt;

    //删除时间
    private Date deAt;

    //删除人
    private Long deBy;

    @AssignID
    private Long id;

    //EMAIL
    @Email(message = "EMAIL必须是Email格式")
    private String email;

    //用户名称
    @NotBlank(message = "用户名称必填")
    private String suName;

    //更新人
    private Long upBy;

    //创建人
    private Long crBy;

    //密码
    private String pwd;

    //修改时间
    private Date upAt;

    //归属机构
    private Long deptId;

    //电话
    @Pattern(regexp = "^$|^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$", message = "手机号码不正确")
    private String tel;

    private String salt;

    //头像
    private String avatar;

    //状态-00:可用,01:禁用
    @NotBlank(message = "状态必填")
    private String status;

    public String getStatusStr() {
        String ret = Consts.STATUS.getVal(this.status);
        return ret;
    }
    //首次登陆时间
    public Date firstLoginAt;

    //用户编号
    @NotBlank(message = "用户编号必填")
    @Pattern(regexp = "[A-Za-z0-9_\\-]+", message = "用户编号必须是字母或者数字")
    private String suCode;
    @Transient
    private Long postId;

}