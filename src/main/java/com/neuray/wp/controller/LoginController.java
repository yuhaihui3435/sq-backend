////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.neuray.wp.Consts;
import com.neuray.wp.annotation.LogDog;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.entity.*;
import com.neuray.wp.kits.JwtKit;
import com.neuray.wp.model.LoginUser;
import com.neuray.wp.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class LoginController extends BaseController {

    public static final String KAPTCHA_CACHE_NAME_ = "kaptcha_cache_name_";
    public static final String ONLINE_USER = "onlineUser";
    public static final String USER_ID = "userId_";

    @Autowired
    private RedisCacheService redisCacheService;
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private SysMenuRightService sysMenuRightService;
    @Autowired
    private SysResRightService sysResRightService;

    public static final String SYSUSER_LOGIN_CACHE_NAME = "sysUser_login_info_";

//    @LogDog(logType = Consts.LOGTYPE.LOGIN, reqSource = Consts.REQSOURCE.INNER)
    @RequestMapping("/login")
    public RespBody login(@RequestBody LoginUser loginUser) {
        String ipPwd = loginUser.getPwd();
        RespBody respBody = new RespBody();
        //检查是否登录启用了验证码
        SysConf sysConf = redisCacheService.getSysConf("loginVcode");
        String scVal = null;
        if (sysConf != null) {
            scVal = sysConf.getScVal();
            //是否启用
            if (scVal.equals(Consts.ENABLESTATUS.YES.getVal())) {
                if (StrUtil.isBlank(loginUser.getVerCode())) {
                    respBody.setCode(RespBody.BUSINESS_ERROR);
                    respBody.setMsg("验证码不能为空!");
                    return respBody;
                }
                //验证码的uuid，存储在redis里
                if (StrUtil.isBlank(loginUser.getVerCodeUUID())) {
                    respBody.setCode(RespBody.BUSINESS_ERROR);
                    respBody.setMsg("验证码获取失败！");
                    return respBody;
                }
                String _vCode = (String) redisCacheService.findVal(KAPTCHA_CACHE_NAME_ + loginUser.getVerCodeUUID());
                if (StrUtil.isBlank(_vCode)) {
                    respBody.setCode(RespBody.BUSINESS_ERROR);
                    respBody.setMsg("验证码过期！");
                    return respBody;
                }
                if (!loginUser.getVerCode().equals(_vCode)) {
                    respBody.setCode(RespBody.BUSINESS_ERROR);
                    respBody.setMsg("验证码不正确！");
                    return respBody;
                }


            }
        }
        //开启短信码认证  需要配合短信发送平台，暂时没有实现。
        sysConf = redisCacheService.getSysConf("loginSmscode");
        if (sysConf != null) {
            scVal = sysConf.getScVal();
            if (scVal.equals(Consts.ENABLESTATUS.YES.getVal())) {
                if (StrUtil.isBlank(loginUser.getSmsCode())) {
                    respBody.setCode(RespBody.BUSINESS_ERROR);
                    respBody.setMsg("短信认证码为空！");
                    return respBody;
                }
            }
        }

        SysUser sysUser = sysUserService.findBySuCodeOrTelOrEmail(loginUser.getLoginname());
        if (sysUser == null) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("账号不存在或者密码错误！");
            return respBody;
        }

        String onlineUserToken = (String) redisCacheService.findVal(ONLINE_USER + sysUser.getId());//查看当前用户在线token
        SysConf userOnlineTactics = (SysConf) redisCacheService.getSysConf("userOnlineTactics");
        if (userOnlineTactics.getScVal().equals("1")) {//先登录为主
            if (StrUtil.isNotBlank(onlineUserToken)) {
                respBody.setCode(RespBody.BUSINESS_ERROR);
                respBody.setMsg("当前账号被使用");
                return respBody;
            }
        } else {//后登录为主
            redisCacheService.delVal(ONLINE_USER + sysUser.getId());//删除在线用户token
            if (StrUtil.isNotBlank(onlineUserToken)) {
                redisCacheService.delVal(SYSUSER_LOGIN_CACHE_NAME + onlineUserToken);//删除当前用户的信息
            }
        }

        Map<String, Object> map = new HashMap<>();
        if (sysUser.getPwd().equals(SecureUtil.hmacMd5(Consts.PWD_SECURE_KEY).digestHex(ipPwd))) {
            //查询所有的关系数据，角色，机构，归属机构，职位，小组件关系，菜单权限，资源权限。
            sysUser = sysUserService.one("sysUser.selectContainRelation", SysUser.builder().id(sysUser.getId()).build());
            //查询归属机构信息
            if (sysUser.getDeptId() != null) {
                Dept dept = deptService.one("dept.sample", Dept.builder().id(sysUser.getDeptId()).build());
                loginUser.setOwnDept(dept);
            }
            List<SysRole> roleUserList = (List<SysRole>) sysUser.get("sysRole");
            loginUser.setRoles(new HashSet<>(roleUserList));
            if (roleUserList != null) {
                Set<Long> roleIds = roleUserList.stream().map(roleUser -> {
                    return roleUser.getId();
                }).collect(Collectors.toSet());
                loginUser.setRoleIds(roleIds);
                if (roleIds.size() > 0) {
                    String roleIdsStr = CollectionUtil.join(roleIds, ",");
                    List<SysMenuRight> sysMenuRights = sysMenuRightService.queryMenuByRole(roleIdsStr);//获得菜单权限
                    List<SysResRight> sysResRights = sysResRightService.queryResByRole(roleIdsStr);//获得资源权限
                    loginUser.setSysMenuRights(new HashSet<>(sysMenuRights));
                    loginUser.setSysResRights(new HashSet<>(sysResRights));
                }
            }

            Set<String> roleNames = loginUser.getRoles() != null ? loginUser.getRoles().stream().map(sysRole -> {
                return sysRole.getRoleName();
            }).collect(Collectors.toSet()) : null;
            Set<Long> roleIds = loginUser.getRoles() != null ? loginUser.getRoles().stream().map(sysRole -> {
                return sysRole.getId();
            }).collect(Collectors.toSet()) : null;
            Set<String> menuRights = loginUser.getSysMenuRights() != null ? loginUser.getSysMenuRights().stream().map(sysMenuRight -> {
                return sysMenuRight.getSmUri();
            }).collect(Collectors.toSet()) : null;
            Set<String> resRights = loginUser.getSysResRights() != null ? loginUser.getSysResRights().stream().map(sysResRight -> {
                return sysResRight.getResUri();
            }).collect(Collectors.toSet()) : null;

            loginUser.setRoleNames(roleNames);
            loginUser.setRoleIds(roleIds);
            loginUser.setSysMenuRightStrs(menuRights);
            loginUser.setSysResRightStrs(resRights);
            //封装到loginUser 系统用户信息载体中
            loginUser.setId(sysUser.getId());
            List<Dept> depts = (List<Dept>) sysUser.get("dept");
            loginUser.setDepts(depts == null ? null : new HashSet<>(depts));
            loginUser.setDetail(sysUser);
            List<Post> posts = (List<Post>) sysUser.get("post");
            loginUser.setPosts(posts == null ? null : new HashSet<>(posts));
//            map.put("loginUser",loginUser);
            map.put("roleIds", roleIds);
            map.put("menuRights", menuRights);
            map.put("resRights", resRights);
            map.put("roleNames", roleNames);
            map.put("userName", sysUser.getSuName());
            map.put("avatar", sysUser.getAvatar());
            map.put("userId", sysUser.getId());
            String token = JwtKit.createJWT(UUID.fastUUID().toString(), map, -1L);//生成jwt token
            redisCacheService.addVal(ONLINE_USER + sysUser.getId(), token);
            redisCacheService.addVal(SYSUSER_LOGIN_CACHE_NAME + token, loginUser);
            SysConf userOnlineDuration = redisCacheService.getSysConf("userOnlineDuration");
            if (loginUser.getRememberMe() != null && loginUser.getRememberMe()) {
                SysConf userRememberMeDuration = redisCacheService.getSysConf("userRememberMeDuration");
                redisCacheService.expired(SYSUSER_LOGIN_CACHE_NAME + token, StrUtil.isBlank(userRememberMeDuration.getScVal()) ? Long.parseLong(userOnlineDuration.getScVal()) : Long.parseLong(userRememberMeDuration.getScVal()), TimeUnit.MINUTES);
                redisCacheService.expired(ONLINE_USER + sysUser.getId(), StrUtil.isBlank(userRememberMeDuration.getScVal()) ? Long.parseLong(userOnlineDuration.getScVal()) : Long.parseLong(userRememberMeDuration.getScVal()), TimeUnit.MINUTES);
            } else {
                redisCacheService.expired(SYSUSER_LOGIN_CACHE_NAME + token, Long.parseLong(userOnlineDuration.getScVal()), TimeUnit.MINUTES);
                redisCacheService.expired(ONLINE_USER + sysUser.getId(), Long.parseLong(userOnlineDuration.getScVal()), TimeUnit.MINUTES);
            }
            Map<String, Object> ret = new HashMap<>();
            ret.put("token", token);
            respBody.setBody(ret);
            respBody.setMsg("登录成功！");
        } else {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("账号不存在或者密码错误！");
            return respBody;
        }


        return respBody;
    }

//    @LogDog(logType = Consts.LOGTYPE.LOGOUT, reqSource = Consts.REQSOURCE.INNER)
    @GetMapping("/logout")
    public RespBody logout(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("token");
        RespBody respBody = new RespBody();
        if (StrUtil.isNotBlank(token)) {
            LoginUser loginUser = (LoginUser) redisCacheService.findVal(SYSUSER_LOGIN_CACHE_NAME + token);
            if (loginUser != null) {
                redisCacheService.delVal(ONLINE_USER + loginUser.getId());
            }
            redisCacheService.delVal(SYSUSER_LOGIN_CACHE_NAME + token);
        }
        respBody.setMsg("退出系统成功！");
        return respBody;
    }

    /**
     * 验证码唯key获取
     *
     * @return
     */
    @GetMapping("/createKaptcha")
    public RespBody genCaptcha() {
        RespBody respBody = new RespBody();
        String uuid = IdUtil.simpleUUID();
        String vCode = RandomUtil.randomString(4);
        redisCacheService.addVal(KAPTCHA_CACHE_NAME_ + uuid, vCode);
        redisCacheService.expired(KAPTCHA_CACHE_NAME_ + uuid, 1, TimeUnit.MINUTES);
        respBody.setBody(uuid);
        return respBody;
    }

    /**
     * 输出验证码图片流
     *
     * @param response
     * @param uuid
     * @throws IOException
     */
    @GetMapping("/createKaptchaImg")
    public void createCaptcha(HttpServletResponse response, @RequestParam String uuid) throws IOException {
        String vCode = (String) redisCacheService.findVal(KAPTCHA_CACHE_NAME_ + uuid);
        BufferedImage bufferedImage = defaultKaptcha.createImage(vCode);
        // Set to expire far in the past.
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bufferedImage, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }
}
