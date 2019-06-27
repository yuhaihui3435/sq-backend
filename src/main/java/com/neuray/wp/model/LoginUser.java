////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.model;
import com.neuray.wp.entity.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Set;

 @Data
public class LoginUser implements Serializable{

    private Long id;

    private String loginname;

    private String pwd;

    private String verCode;

    private String verCodeUUID;

    private String smsCode;

    private Boolean rememberMe;

    private String token;

    private Set<SysMenuRight> sysMenuRights;

    private Set<SysResRight> sysResRights;

    private Dept ownDept;

    private Set<Dept> depts;

    private Set<Post> posts;

    private Set<SysRole> roles;

    private SysUser detail;

    private Set<String> roleNames;

    private Set<Long> roleIds;

    private Set<String> sysMenuRightStrs;

    private Set<String> sysResRightStrs;
}
