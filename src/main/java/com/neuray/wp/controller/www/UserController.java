////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2019. 东睿科技有限公司.保留所有权利
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.www;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.neuray.wp.Consts;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.entity.doctor.Doctor;
import com.neuray.wp.entity.user.UserInfo;
import com.neuray.wp.entity.user.UserLogin;
import com.neuray.wp.kits.CookieKit;
import com.neuray.wp.kits.JwtKit;
import com.neuray.wp.kits.ReqKit;
import com.neuray.wp.model.MemberLoginDto;
import com.neuray.wp.service.RedisCacheService;
import com.neuray.wp.service.doctor.DoctorService;
import com.neuray.wp.service.sms.SMSService;
import com.neuray.wp.service.user.UserInfoService;
import com.neuray.wp.service.user.UserLoginService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author:小听风 创建时间:2019/7/8
 * 版本:v1.0
 * @Description:
 */
@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    public static final String PREFIX_SMSCODE = "SMSCODE_";


    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private RedisCacheService redisCacheService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private SMSService smsService;

    /**
     * 会员注册
     *
     * @param userLogin
     * @param smsCode
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public RespBody register(@RequestBody UserLogin userLogin, @RequestParam String smsCode) {
        if (StrUtil.isBlank(userLogin.getPhone())) {
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("请填写手机号").build();
        } else if (StrUtil.isBlank(userLogin.getPwd())) {
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("请填写密码").build();
        } else if (StrUtil.isBlank(smsCode)) {
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("请填写短信验证码").build();
        }
        String str = (String) redisCacheService.findVal(PREFIX_SMSCODE + userLogin.getPhone());
        if (StrUtil.isBlank(str)) {
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("短信验证码过期").build();
        } else if (!str.equals(smsCode)) {
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("短信验证码不正确").build();
        }

        String account = RandomUtil.randomString(9);
        Boolean bl = true;
        while (bl) {
            if (userLoginService.checkAccount(account, null).isEmpty()) {
                bl = false;
            } else {
                account = RandomUtil.randomString(9);
            }
        }
        userLogin.setPwd(SecureUtil.hmacMd5(Consts.PWD_SECURE_KEY).digestHex(userLogin.getPwd()));
        userLogin.setAccount(account);
        userLogin.setStatus(Consts.STATUS.AVAILABLE.getCode());
        userLogin.setType(Consts.USERLOGIN_TYPE.MEMBER.getCode());
        userLoginService.insertAutoKey(userLogin);
        return RespBody.builder().code(RespBody.SUCCESS).msg("会员注册成功").build();
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    @GetMapping("/sendSmsCode/{phone}")
    @ResponseBody
    public RespBody<Object> sendSmsCode(@PathVariable("phone") String phone) {
        if (StrUtil.isBlank(phone)) {
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("请填写手机号").build();
        }
        //缓存中检查是否存在验证码，有删掉
        if (redisCacheService.exist(PREFIX_SMSCODE + phone)) {
            redisCacheService.delVal(PREFIX_SMSCODE + phone);
        }
        List<UserLogin> userLoginList = userLoginService.checkPhone(phone, null);
        if (userLoginList.isEmpty()) {
            //发送短信验证码
            String code = RandomUtil.randomNumbers(4);
            //发送验证码
            smsService.sendSms(phone, code);
            //缓存验证码
            redisCacheService.addVal(PREFIX_SMSCODE + phone, code);
            redisCacheService.expired(PREFIX_SMSCODE + phone, 30, TimeUnit.MINUTES);
            return RespBody.builder().code(RespBody.SUCCESS).msg("验证码发送成功").build();
        } else {
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("此手机号已注册").build();
        }
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    @GetMapping("/sendSmsCodeSend/{phone}")
    @ResponseBody
    public RespBody<Object> sendSmsCodeSend(@PathVariable("phone") String phone) {
        if (StrUtil.isBlank(phone)) {
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("请填写手机号").build();
        }
        //缓存中检查是否存在验证码，有删掉
        if (redisCacheService.exist(PREFIX_SMSCODE + phone)) {
            redisCacheService.delVal(PREFIX_SMSCODE + phone);
        }
        //发送短信验证码
        String code = RandomUtil.randomNumbers(4);
        //发送验证码
        smsService.sendSms(phone, code);
        //缓存验证码
        redisCacheService.addVal(PREFIX_SMSCODE + phone, code);
        redisCacheService.expired(PREFIX_SMSCODE + phone, 30, TimeUnit.MINUTES);
        return RespBody.builder().code(RespBody.SUCCESS).msg("验证码发送成功").build();
    }

    @PostMapping("/login")
    @ResponseBody
    public RespBody login(@RequestBody MemberLoginDto param, HttpServletRequest request, HttpServletResponse response) {

        UserLogin userLogin = userLoginService.findByAccountOrTelOrEmail(param.getAccount());
        //没有匹配到会员
        if (userLogin == null) {
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("账号不存在或已注销").build();
            //是否被锁定，五分钟内密码联系输错3次，被停用半小时
        } else if (redisCacheService.exist(Consts.MEMBER_LOCKED + userLogin.getId())) {
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("账号被锁定,原因:" + (String) redisCacheService.findVal(Consts.MEMBER_LOCKED + userLogin.getId())).build();
        }
        //账户状态是否正常
        else if (userLogin.getStatus().equals(Consts.STATUS.DISABLE.getCode())) {
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("账号被停用").build();
        }
        //密码验证失败
        else if (!userLogin.getPwd().equals(SecureUtil.hmacMd5(Consts.PWD_SECURE_KEY).digestHex(param.getPwd()))) {
            Integer tryCount = (Integer) redisCacheService.findVal(Consts.MEMBER_TRY_COUNT + userLogin.getId());
            //是否已经尝试过，第一次
            if (tryCount == null) {
                tryCount = 1;
                redisCacheService.addVal(Consts.MEMBER_TRY_COUNT + userLogin.getId(), tryCount);
                redisCacheService.expired(Consts.MEMBER_TRY_COUNT + userLogin.getId(), 5, TimeUnit.MINUTES);
            } else {
                //已经尝试过
                tryCount = tryCount + 1;
                redisCacheService.addVal(Consts.MEMBER_TRY_COUNT + userLogin.getId(), tryCount);
            }
            //尝试次数达到上限，被锁定
            if (Consts.MEMBER_LOGIN_TRY_COUNT.equals(tryCount)) {
                redisCacheService.addVal(Consts.MEMBER_LOCKED + userLogin.getId(), "短时间内密码多次错误，账号被锁定半个小时");
                redisCacheService.expired(Consts.MEMBER_LOCKED + userLogin.getId(), 30, TimeUnit.MINUTES);
            }
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("密码错误，还能尝试" + (Consts.MEMBER_LOGIN_TRY_COUNT - tryCount) + "次！").build();
        } else {
            //登录成功，清楚缓存
            redisCacheService.delVal(Consts.MEMBER_LOCKED + userLogin.getId());
            redisCacheService.delVal(Consts.MEMBER_TRY_COUNT + userLogin.getId());
            if (redisCacheService.exist(Consts.CURR_MEMBER + userLogin.getId())) {
                String str = (String) redisCacheService.findVal(Consts.CURR_MEMBER + userLogin.getId());
                redisCacheService.delVal(str);
                redisCacheService.delVal(Consts.CURR_MEMBER + userLogin.getId());//强制删除当前在线的账户
            }
            //记录最后一次登录时间
            MemberLoginDto memberLoginDto = new MemberLoginDto();
            memberLoginDto.setUserLoginId(userLogin.getId());
            memberLoginDto.setLastLoginAt(new Date());
            memberLoginDto.setLastLoginIp(ReqKit.getRemortIP(request));
            memberLoginDto.setUserLogin(userLogin);
            memberLoginDto.setAccount(userLogin.getAccount());
            if (Consts.USERLOGIN_TYPE.MEMBER.getCode().equals(userLogin.getType())) {
                UserInfo userInfo = userInfoService.tplOne(UserInfo.builder().loginId(userLogin.getId()).build());
                if (userInfo != null) {
                    memberLoginDto.setUserInfo(userInfo);
                    memberLoginDto.setMemberType(Consts.USERLOGIN_TYPE.MEMBER.getCode());
                    memberLoginDto.setMemberInfoComplete(true);
                }

            } else if (Consts.USERLOGIN_TYPE.DOCTOR.getCode().equals(userLogin.getType())) {
                Doctor doctor = doctorService.tplOne(Doctor.builder().loginId(userLogin.getId()).build());
                if (doctor != null) {
                    memberLoginDto.setDoctor(doctor);
                    memberLoginDto.setMemberType(Consts.USERLOGIN_TYPE.DOCTOR.getCode());
                    memberLoginDto.setMemberInfoComplete(true);
                }
            }

            Map<String, Object> map = new HashMap<>();
            map.put("memberLoginInfo", memberLoginDto);

            String token = JwtKit.createJWT(UUID.fastUUID().toString(), map, -1L);
            redisCacheService.addVal(Consts.CURR_MEMBER + userLogin.getId(), token);
            redisCacheService.addVal(token, memberLoginDto);

            /**
             * 记住我，可免登陆7天
             */
            if (param.getRememberMe()) {
                redisCacheService.expired(Consts.CURR_MEMBER + userLogin.getId(), 7, TimeUnit.DAYS);
                redisCacheService.expired(token, 7, TimeUnit.DAYS);
            } else {
                redisCacheService.expired(Consts.CURR_MEMBER + userLogin.getId(), 8, TimeUnit.HOURS);
                redisCacheService.expired(token, 8, TimeUnit.HOURS);
            }

            return RespBody.builder().code(RespBody.SUCCESS).msg("登录成功").body(token).build();

        }
    }

    @PostMapping("/getLogin")
    public Object getLogin(HttpServletRequest request) {
        String token = request.getHeader("token");
        return redisCacheService.findVal(token);
    }

    /**
     * 退出系统
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    @ResponseBody
    public RespBody logout(HttpServletRequest request) {
        String token = CookieKit.getUid(request, Consts.MEMBER_TOKEN);
        if (StrUtil.isNotBlank(token)) {
            MemberLoginDto memberLoginDto = (MemberLoginDto) redisCacheService.findVal(token);
            redisCacheService.delVal(token);
            if (memberLoginDto != null) {
                redisCacheService.delVal(Consts.CURR_MEMBER + memberLoginDto.getUserLoginId());
            }
        }
        return RespBody.builder().code(RespBody.SUCCESS).msg("退出成功").build();
    }

    /**
     * 修改密码
     *
     * @param param
     * @param request
     * @return
     */
    @PostMapping("/modifyPwd")
    @ResponseBody
    public RespBody modifyPwd(@RequestBody MemberLoginDto param, HttpServletRequest request) {
//        String token = CookieKit.getUid(request, Consts.MEMBER_TOKEN);
//        MemberLoginDto memberLoginDto = (MemberLoginDto) redisCacheService.findVal(token);
//        UserLogin userLogin = userLoginService.one(memberLoginDto.getUserLoginId());
        UserLogin userLogin = userLoginService.one(param.getUserLoginId());
        String pwd = userLogin.getPwd();
        if (SecureUtil.hmacMd5(Consts.PWD_SECURE_KEY).digestHex(param.getPwd()).equals(pwd)) {
            userLogin.setPwd(SecureUtil.hmacMd5(Consts.PWD_SECURE_KEY).digestHex(param.getNewPwd()));
            userLoginService.update(userLogin);
        } else {
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("原始密码不正确").build();
        }
        return RespBody.builder().code(RespBody.SUCCESS).msg("密码修改成功").build();

    }

    /**
     * @return com.neuray.wp.core.RespBody
     * @Description
     * @Param 修改手机号
     * @Author zzq
     * @Date 2019/7/26 8:49
     **/
    @PostMapping("/modifyPhone")
    public RespBody modifyPhone(@RequestBody UserLogin userLogin, String smsCode) {
        String str = (String) redisCacheService.findVal(PREFIX_SMSCODE + userLogin.getPhone());
        if (StrUtil.isBlank(str)) {
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("短信验证码过期").build();
        } else if (!str.equals(smsCode)) {
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("短信验证码不正确").build();
        }
        UserLogin userLogin1 = userLoginService.one(userLogin.getId());
        userLogin1.setPhone(userLogin.getNewPhone());
        userLoginService.update(userLogin1);
        return RespBody.builder().code(RespBody.SUCCESS).msg("手机修改成功").build();
    }

    /**
     * @return com.neuray.wp.core.RespBody
     * @Description
     * @Param 修改邮箱
     * @Author zzq
     * @Date 2019/7/26 8:49
     **/
    @PostMapping("/modifyEmail")
    public RespBody modifyEmail(@RequestBody UserLogin userLogin, String smsCode) {
        String str = (String) redisCacheService.findVal(PREFIX_SMSCODE + userLogin.getPhone());
        if (StrUtil.isBlank(str)) {
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("短信验证码过期").build();
        } else if (!str.equals(smsCode)) {
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("短信验证码不正确").build();
        }
        UserLogin userLogin1 = userLoginService.one(userLogin.getId());
        userLogin1.setEmail(userLogin.getNewEmail());
        userLoginService.update(userLogin1);
        return RespBody.builder().code(RespBody.SUCCESS).msg("邮箱修改成功").build();
    }

    /**
     * 会员详细信息更新
     *
     * @param userInfo
     * @return
     */
    @PostMapping("/modifyUserInfo")
    @ResponseBody
    public RespBody modifyUserInfo(@RequestBody UserInfo userInfo) {
        if (userInfo.getId() != null) {
            userInfoService.update(userInfo);
        } else {
            userInfoService.insertAutoKey(userInfo);
        }
        return RespBody.success("详细信息更新成功");
    }

    /**
     * 医生信息更新
     *
     * @param doctor
     * @return
     */
    @PostMapping("/modifyDoctorInfo")
    @ResponseBody
    public RespBody modifyUserInfo(@RequestBody Doctor doctor) {
        doctorService.update(doctor);
        return RespBody.success("详细信息更新成功");
    }

    @PostMapping("/info")
    public UserInfo getUserInfo(@RequestParam String userId) {
        return userInfoService.one(userId);
    }

    @PostMapping("/userInfo")
    public UserInfo queryUserInfo(@RequestBody UserInfo condition) {
        return userInfoService.one("user.userInfo.sample", condition);
    }

    @PostMapping("/userLogin")
    public UserLogin queryUserLogin(@RequestBody UserLogin condition) {
        return userLoginService.one(condition.getId());
    }
}
