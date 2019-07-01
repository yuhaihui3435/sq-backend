////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.user;


import cn.hutool.core.util.StrUtil;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.entity.user.UserInfo;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.entity.user.UserLogin;
import com.neuray.wp.service.user.UserInfoService;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.kits.ValidationKit;
import com.neuray.wp.service.user.UserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/userInfo")
@Slf4j
public class UserInfoController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserLoginService userLoginService;
    /**
         *
         * 条件查询
         * @param condition
         * @return
         */
        @PostMapping("/query")
        public List<UserInfo> query(@RequestBody UserInfo condition) {
            return userInfoService.many("user.userInfo.sample", condition);
        }
    /**
     *
     * 分页
     * @param condition
     * @return
     */
    @PostMapping("/page")
    public PageQuery page(@RequestBody UserInfo condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setParas(condition);
        pageQuery = userInfoService.page("user.userInfo.selectUserAllData", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param userInfo
     * @return
     */
    @PostMapping("/save")
    public RespBody save(@RequestBody UserInfo userInfo) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(userInfo);
        userInfoService.insertAutoKey(userInfo);
        respBody.setMsg("新增用户详细信息成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param userInfo
     * @return
     */
    @PostMapping("/update")
    public RespBody update(@RequestBody UserInfo userInfo) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(userInfo);
        userInfoService.update(userInfo);
        respBody.setMsg("更新用户详细信息成功");
        return respBody;
    }

    /**
     * 删除
     *
     * @param param
     * @return
     */
    @PostMapping("/del")
    public RespBody del(@RequestBody Map<String,String> param) {
        RespBody respBody = new RespBody();
        if(StrUtil.isBlank(param.get("ids"))){throw new LogicException("删除操作失败，缺少删除数据");}
        String[] idArray=StrUtil.split(param.get("ids"),",");
        for(String id:idArray){
            UserInfo userInfo=userInfoService.one(Long.parseLong(id));
            UserLogin userLogin=userLoginService.one(userInfo.getLoginId());
            userLogin.setDeAt(new Date());
            userLoginService.updateTplById(userLogin);
        }
        respBody.setMsg("删除用户详细信息成功");
        return respBody;
    }

    /**
    * 详细
    *
    * @param id
    * @return
    */
    @PostMapping("/view/{id}")
    public UserInfo view(@PathVariable("id") Long id) {
        UserInfo userInfo=userInfoService.one("user.userInfo.selectUserAllData",UserInfo.builder().id(id).build());
        return userInfo;
    }

}
