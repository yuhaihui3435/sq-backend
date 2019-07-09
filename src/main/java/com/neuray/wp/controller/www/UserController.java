////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2019. 东睿科技有限公司.保留所有权利
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.www;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.entity.user.UserLogin;
import com.neuray.wp.service.RedisCacheService;
import com.neuray.wp.service.user.UserInfoService;
import com.neuray.wp.service.user.UserLoginService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author:小听风 创建时间:2019/7/8
 * 版本:v1.0
 * @Description:
 */
@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    public static final String PREFIX_SMSCODE="SMSCODE_";

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private RedisCacheService redisCacheService;

    @PostMapping("/register")
    @ResponseBody
    public RespBody register(@ModelAttribute UserLogin userLogin,@RequestParam String smsCode){
        if(StrUtil.isBlank(userLogin.getPhone())){
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("请填写手机号").build();
        }
        else if(StrUtil.isBlank(userLogin.getPwd())){
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("请填写密码").build();
        }else if(StrUtil.isBlank(smsCode)){
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("请填写短信验证码").build();
        }
        String str=(String)redisCacheService.findVal(PREFIX_SMSCODE+userLogin.getPhone());
        if(StrUtil.isBlank(str)){
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("短信验证码过期").build();
        }else if (!str.equals(smsCode)){
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("短信验证码不正确").build();
        }

        String account=RandomUtil.randomString(9);
        Boolean bl=true;
        while(bl) {
            if (userLoginService.checkAccount(account, null).isEmpty()) {
                bl=false;
            }else{
                account=RandomUtil.randomString(9);
            }
        }

        userLogin.setAccount(account);




        return null;
    }

    @GetMapping("/sendSmsCode/{phone}")
    public RespBody<Object> sendSmsCode(@PathVariable("phone") String phone){
        //缓存中检查是否存在验证码，有删掉


        List<UserLogin> userLoginList=userLoginService.checkPhone(phone,null);
        if(userLoginList.isEmpty()){
            //发送短信验证码
            String code=RandomUtil.randomNumbers(4);
            //发送验证码

            //缓存验证码

            return RespBody.builder().code(RespBody.SUCCESS).msg("验证码发送成功，半个小时内有效。").build();
        }else{
            return RespBody.builder().code(RespBody.BUSINESS_ERROR).msg("此手机号已注册").build();
        }
    }



}
