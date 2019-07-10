package com.neuray.wp.model;


import com.neuray.wp.entity.doctor.Doctor;
import com.neuray.wp.entity.user.UserInfo;
import com.neuray.wp.entity.user.UserLogin;
import lombok.Data;

import java.util.Date;

/**
 * 会员登录Dto
 * 时间 2019/7/9
 * @author 小听风
 * @version v1.0
 * @see
 * @since
 */
@Data
public class MemberLoginDto {
    private String newPwd;
    private Long userLoginId;

    private String account;

    private String pwd;

    private String lastLoginIp;

    private Date lastLoginAt;

    private UserLogin userLogin;

    private UserInfo userInfo;

    private Doctor doctor;

    private String memberType;
    /**
     * 会员信息是否完善
     */
    private Boolean memberInfoComplete=false;
    /**
     * 记住我
     */
    private Boolean rememberMe=false;


}
