////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.neuray.wp.Consts;
import com.neuray.wp.annotation.LogDog;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.entity.*;
import com.neuray.wp.kits.ValidationKit;
import com.neuray.wp.model.UserDeptDto;
import com.neuray.wp.model.UserRoleDto;
import com.neuray.wp.service.*;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/sysUser")
@Slf4j
public class SysUserController extends BaseController {

    public static final String PWD_SECURE_KEY = "neuray2503@neuray.cn";
    @Value("${pic.user.path}")
    private String picUserPath;
    @Value("${pic.root.path}")
    private String picRootPath;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserDeptService userDeptService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private UserPostService userPostService;
    @Autowired
    private RoleUserService roleUserService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private UserWidgetService userWidgetService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private WidgetService widgetService;


    @LogDog(logType = Consts.LOGTYPE.QUERY,reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/query")
    public List<SysUser> query(@RequestBody SysUser condition) {
        return sysUserService.many("sysUser.sample", condition);
    }

    /**
     * 分页
     *
     * @param condition
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY,reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/page")
    public PageQuery page(@RequestBody SysUser condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = sysUserService.page("sysUser.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param sysUser
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.SAVE,reqSource = Consts.REQSOURCE.INNER)
    @Transactional
    @PostMapping("/save")
    public RespBody save(@RequestBody SysUser sysUser) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(sysUser);

        List<SysUser> list = sysUserService.checkTel(sysUser.getTel(), null);
        if (list != null && !list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("电话已存在");
            return respBody;
        }
        list = sysUserService.checkSuCode(sysUser.getSuCode(), null);
        if (list != null && !list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("用户编号已存在");
            return respBody;
        }
        list = sysUserService.checkEmail(sysUser.getEmail(), null);
        if (list != null && !list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("EMAIL已存在");
            return respBody;
        }
        sysUser.setCrBy(currLoginUser().getId());
        sysUser.setUpBy(currLoginUser().getId());
        sysUser.setPwd(SecureUtil.hmacMd5(PWD_SECURE_KEY).digestHex(sysUser.getSuCode()));
        sysUserService.insertAutoKey(sysUser);
        List<Integer> postIds = (ArrayList<Integer>) sysUser.get("posts");
        if (postIds != null) {
            UserPost userPost = null;
            for (Integer postId : postIds) {
                userPost = new UserPost();
                userPost.setCrBy(currLoginUser().getId());
                userPost.setUpBy(currLoginUser().getId());
                userPost.setSysUserId(sysUser.getId());
                userPost.setPostId(postId.longValue());
                userPostService.insertAutoKey(userPost);
            }
        }
        respBody.setMsg("新增系统用户成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param sysUser
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE,reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/update")
    public RespBody update(@RequestBody SysUser sysUser) {

        RespBody respBody = new RespBody();
        ValidationKit.validate(sysUser);
        List<SysUser> list = sysUserService.checkTel(sysUser.getTel(), sysUser.getId());
        if (list != null && !list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("电话已存在");
            return respBody;
        }
        list = sysUserService.checkSuCode(sysUser.getSuCode(), sysUser.getId());
        if (list != null && !list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("用户编号已存在");
            return respBody;
        }
        list = sysUserService.checkEmail(sysUser.getEmail(), sysUser.getId());
        if (list != null && !list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("EMAIL已存在");
            return respBody;
        }
        List<UserPost> userPostList = userPostService.findBySysUserId(sysUser.getId());
        userPostList.stream().forEach(item -> {
            item.setDeAt(new Date());
            item.setDeBy(currLoginUser().getId());
            userPostService.update(item);
        });
        List<Integer> postIds = (ArrayList<Integer>) sysUser.get("posts");
        if (postIds != null) {
            UserPost userPost = null;
            for (Integer postId : postIds) {
                userPost = new UserPost();
                userPost.setCrBy(currLoginUser().getId());
                userPost.setUpBy(currLoginUser().getId());
                userPost.setSysUserId(sysUser.getId());
                userPost.setPostId(postId.longValue());
                userPostService.insertAutoKey(userPost);
            }
        }
        sysUser.setUpBy(currLoginUser().getId());
        sysUserService.update(sysUser);
        respBody.setMsg("更新系统用户成功");
        return respBody;
    }

    /**
     * 删除
     *
     * @param param
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.LOGICDEL,reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/del")
    public RespBody del(@RequestBody Map<String, String> param) {
        RespBody respBody = new RespBody();
        if (StrUtil.isBlank(param.get("ids"))) throw new LogicException("删除操作失败，缺少删除数据");
        String[] idArray = StrUtil.split(param.get("ids"), ",");
        for (String id : idArray) {
            SysUser sysUser = sysUserService.one(Long.parseLong(id));
            sysUser.setDeAt(new Date());
            sysUser.setDeBy(currLoginUser().getId());
            sysUserService.updateTplById(sysUser);
        }
        respBody.setMsg("删除系统用户成功");
        return respBody;
    }

    /**
     * 详细
     *
     * @param id
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY,reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/view/{id}")
    public SysUser view(@PathVariable("id") Long id) {
        SysUser sysUser = sysUserService.one("sysUser.sample", SysUser.builder().id(id).build());
        return sysUser;
    }

    /**
     * 保存用户机构关系
     *
     * @param userDeptDto
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.SAVE,reqSource = Consts.REQSOURCE.INNER)
    @Transactional
    @PostMapping("/saveUserDeptSet")
    public RespBody saveUserDeptSet(@RequestBody UserDeptDto userDeptDto) {
        RespBody respBody = new RespBody();
        List<UserDept> userDeptList = userDeptService.tpl(UserDept.builder().sysUserId(userDeptDto.getUserId()).build());
        userDeptList.stream().forEach(userDept1 -> {
            userDept1.setDeAt(new Date());
            userDept1.setDeBy(currLoginUser().getId());
            userDeptService.update(userDept1);
        });
        for (UserDept userDept : userDeptDto.getUserDepts()) {
            userDept.setUpBy(currLoginUser().getId());
            userDept.setCrBy(currLoginUser().getId());
            userDept.setEffect(userDept.getEffect() == null ? new Date() : userDept.getEffect());
            userDeptService.insertAutoKey(userDept);
        }
        respBody.setMsg("用户跨部门关系设置成功");
        return respBody;
    }

    /**
     * 查询系统用户详细，附带查询部门关联关系数据：跨机构，角色信息
     *
     * @param id
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY,reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/oneContainRelation/{id}")
    public SysUser oneContainRelation(@PathVariable("id") Long id) {
        SysUser sysUser = sysUserService.one("sysUser.selectContainRelation", SysUser.builder().id(id).build());
        if (sysUser.getDeptId() != null)
            sysUser.set("ownDept", deptService.one("dept.sample", Dept.builder().id(sysUser.getDeptId()).build()));
        return sysUser;
    }

    /**
     * 用户角色关系保存
     *
     * @param userRoleDto
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.SAVE,reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/saveUserRoleSet")
    public RespBody saveUserRoleSet(@RequestBody UserRoleDto userRoleDto) {
        RespBody respBody = new RespBody();
        List<RoleUser> roleUserList = roleUserService.tpl(RoleUser.builder().sysUserId(userRoleDto.getSysUserId()).build());
        roleUserList.stream().forEach(roleUser -> {
            roleUser.setDeAt(new Date());
            roleUser.setDeBy(currLoginUser().getId());
            roleUserService.update(roleUser);
        });
        for (RoleUser roleUser : userRoleDto.getRoleUsers()) {
            roleUser.setUpBy(currLoginUser().getId());
            roleUser.setCrBy(currLoginUser().getId());
            roleUserService.insertAutoKey(roleUser);
        }
        //根据此用户查询原有用户小组件关系对象，删除
        List<UserWidget> userWidgetList = userWidgetService.queryUserWidgetByUserId(userRoleDto.getSysUserId());
        for (int i = 0; i < userWidgetList.size(); i++) {
            UserWidget userWidget = userWidgetList.get(i);
            userWidget.setDeBy(currLoginUser().getId());
            userWidget.setDeAt(new Date());
            userWidgetService.update(userWidget);
        }
        //查询出来该用户所有的角色，在查询出这些角色所有的小组件（去重查询）
        List<SysRole> listRole = sysRoleService.queryRoleByUserId(userRoleDto.getSysUserId());
        String roleIds = "";
        for (int i = 0; i < listRole.size(); i++) {
            //组织角色id串
            if (StringUtils.isNotBlank(roleIds)) {
                roleIds += ",";
            }
            roleIds += listRole.get(i).getId() + "";
        }
        //查询所有的小组件
        List<Widget> listWidget = widgetService.queryWidgetByRoleDis(roleIds);
        for (int i = 0; i < listWidget.size(); i++) {
            UserWidget userWidget = new UserWidget();
            userWidget.setSysUserId(userRoleDto.getSysUserId());
            userWidget.setWidgetId(listWidget.get(i).getId());
            userWidget.setDftW(listWidget.get(i).getDftW());
            userWidget.setDefH(listWidget.get(i).getDefH());
            userWidget.setCrBy(currLoginUser().getId());
            userWidget.setUpBy(currLoginUser().getId());
            userWidget.setType(0L);
            userWidget.setPointX(0L);
            userWidget.setPointY(0L);
            userWidgetService.insertAutoKey(userWidget);
        }
        respBody.setMsg("用户角色关系设置成功");
        return respBody;
    }

    /**
     * 密码重置
     *
     * @param id
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE,reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/pwdReset/{id}")
    public RespBody pwdReset(@PathVariable("id") Long id) {
        SysUser sysUser = sysUserService.one(id);
        String randomPwd = RandomUtil.randomString(6);
        sysUser.setPwd(SecureUtil.hmacMd5(PWD_SECURE_KEY).digestHex(randomPwd));
        sysUser.setFirstLoginAt(null);
        sysUser.setUpBy(currLoginUser() != null ? currLoginUser().getId() : null);
        sysUserService.update(sysUser);
        if (StrUtil.isNotBlank(sysUser.getEmail())) {
            //发送邮件通知
        }
        RespBody respBody = new RespBody();
        respBody.setMsg("重置密码成功");
        respBody.setBody(randomPwd);

        return respBody;
    }

    /**
     * 关于我提交方法
     *
     * @return : com.neuray.wp.core.RespBody
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE,reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/aboutMe")
    public RespBody aboutMe(@RequestBody Map<String, String> param) {
        RespBody respBody = new RespBody();
        //根据id查询用户对象
        SysUser sysUser = sysUserService.tplOne(SysUser.builder().id(Long.parseLong(param.get("id"))).build());
        sysUser.setSuName(param.get("suName"));
        sysUser.setTel(param.get("tel"));
        sysUser.setEmail(param.get("email"));
        sysUser.setAvatar(param.get("avatar"));
        //判断密码是否填写
        if (StringUtils.isNotBlank(param.get("password")) && StringUtils.isNotBlank(param.get("newPassword")) && StringUtils.isNotBlank(param.get("passNewPassword"))) {
            //如果填写，比对密码是否是原密码
            if (SecureUtil.hmacMd5(PWD_SECURE_KEY).digestHex(param.get("password")).equals(sysUser.getPwd())) {
                sysUser.setPwd(SecureUtil.hmacMd5(PWD_SECURE_KEY).digestHex(param.get("newPassword")));
            } else {
                respBody.setMsg("原密码不正确");
                respBody.setCode(RespBody.BUSINESS_ERROR);
            }
        }
        sysUserService.update(sysUser);
        respBody.setCode(RespBody.SUCCESS);
        respBody.setMsg("操作成功");
        return respBody;
    }

    /**
     * 上传头像
     *
     * @param picture :
     * @param request :
     * @return : com.neuray.wp.core.RespBody
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("avatar") MultipartFile picture, HttpServletRequest request) {
        String picPath = StrUtil.isNotBlank(picUserPath) ? picUserPath : ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/upload/userPic/";
        log.info(picPath);
        if (!FileUtil.exist(picPath)) FileUtil.mkdir(picPath);
        //获取原始文件名称(包含格式)
        String originalFileName = picture.getOriginalFilename();
        System.out.println("原始文件名称：" + originalFileName);
        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        System.out.println("文件类型：" + type);
        //获取文件名称（不包含格式）
//        String name = "sysAvatar";
        //设置文件新名称: 当前时间+文件名称（不包含格式）
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = currLoginUser().getLoginname() + "." + type;
        System.out.println("新文件名称：" + fileName);
        //在指定路径下创建一个文件
        File targetFile = new File(picPath, fileName);
        //将文件保存到服务器指定位置
        try {
            picture.transferTo(targetFile);
            System.out.println("上传成功");
            return new Result(true, fileName);
        } catch (IOException e) {
            System.out.println("上传失败");
            e.printStackTrace();
            return new Result(false, fileName);
        }
    }

    /**
     * 读取图片
     *
     * @param picName  :
     * @param response :
     * @return : void
     */
    @GetMapping(value = "/loadPic")
    public void loadPic(@RequestParam(name = "picName", defaultValue = "") String picName, HttpServletResponse response) {
        String picPath = "";
        if (StringUtils.isNotBlank(picName) && !"null".equals(picName)) {
            picPath = StrUtil.isNotBlank(picUserPath) ? picUserPath : ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/upload/userpic/";
        } else {
            SysConf sysConf = cacheService.getSysConf("sysAvatar");
            picName = sysConf.getScVal();
            picPath = StrUtil.isNotBlank(picRootPath) ? picRootPath : ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/upload/";
        }
        ServletOutputStream out = null;
        FileInputStream ips = null;
        try {
            ips = new FileInputStream(new File(picPath + picName));
            response.setContentType("multipart/form-data");
            out = response.getOutputStream();
            int i = 0;
            byte[] buffer = new byte[4096];
            while ((i = ips.read(buffer)) != -1) {
                out.write(buffer, 0, i);
            }
            out.flush();
            ips.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ips != null) {
                try {
                    ips.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除图片
     *
     * @return : com.neuray.wp.core.RespBody
     */
    @PostMapping("delFile")
    public RespBody delFile(@RequestBody Map<String, String> param) {
        RespBody respBody = new RespBody();
        String picPath = StrUtil.isNotBlank(picUserPath) ? picUserPath : ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/upload/userpic/";
        return respBody;
    }

    public static void main(String[] args) {
        System.out.printf(SecureUtil.hmacMd5(PWD_SECURE_KEY).digestHex("CPJ002"));
    }
}
