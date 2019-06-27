////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.user;


import cn.hutool.core.util.StrUtil;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.entity.user.UserLogin;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.service.user.UserLoginService;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.kits.ValidationKit;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/userLogin")
@Slf4j
public class UserLoginController extends BaseController {

    @Autowired
    private UserLoginService userLoginService;
    /**
         *
         * 条件查询
         * @param condition
         * @return
         */
        @PostMapping("/query")
        public List<UserLogin> query(@RequestBody UserLogin condition) {
            return userLoginService.many("userLogin.sample", condition);
        }
    /**
     *
     * 分页
     * @param condition
     * @return
     */
    @PostMapping("/page")
    public PageQuery page(@RequestBody UserLogin condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = userLoginService.page("userLogin.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param userLogin
     * @return
     */
    @PostMapping("/save")
    public RespBody save(@RequestBody UserLogin userLogin) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(userLogin);
                            List<UserLogin> list=userLoginService.checkWxOpenId(userLogin.getWxOpenId(),null);
                        if(list!=null&&!list.isEmpty()){
                            respBody.setCode(RespBody.BUSINESS_ERROR);
                            respBody.setMsg("已存在");
                            return respBody;
                        }
                            list=userLoginService.checkPhone(userLogin.getPhone(),null);
                        if(list!=null&&!list.isEmpty()){
                            respBody.setCode(RespBody.BUSINESS_ERROR);
                            respBody.setMsg("已存在");
                            return respBody;
                        }
                            list=userLoginService.checkEmail(userLogin.getEmail(),null);
                        if(list!=null&&!list.isEmpty()){
                            respBody.setCode(RespBody.BUSINESS_ERROR);
                            respBody.setMsg("已存在");
                            return respBody;
                        }
                            list=userLoginService.checkQqOpenId(userLogin.getQqOpenId(),null);
                        if(list!=null&&!list.isEmpty()){
                            respBody.setCode(RespBody.BUSINESS_ERROR);
                            respBody.setMsg("已存在");
                            return respBody;
                        }
                            list=userLoginService.checkAccount(userLogin.getAccount(),null);
                        if(list!=null&&!list.isEmpty()){
                            respBody.setCode(RespBody.BUSINESS_ERROR);
                            respBody.setMsg("已存在");
                            return respBody;
                        }
        userLogin.setUpBy(currLoginUser().getId());
        userLoginService.insertAutoKey(userLogin);
        respBody.setMsg("新增用户登录信息成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param userLogin
     * @return
     */
    @PostMapping("/update")
    public RespBody update(@RequestBody UserLogin userLogin) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(userLogin);
                                List<UserLogin> list=userLoginService.checkWxOpenId(userLogin.getWxOpenId(),userLogin.getId());
                                if(list!=null&&!list.isEmpty()){
                                    respBody.setCode(RespBody.BUSINESS_ERROR);
                                    respBody.setMsg("已存在");
                                    return respBody;
                                }
                                list=userLoginService.checkPhone(userLogin.getPhone(),userLogin.getId());
                                if(list!=null&&!list.isEmpty()){
                                    respBody.setCode(RespBody.BUSINESS_ERROR);
                                    respBody.setMsg("已存在");
                                    return respBody;
                                }
                                list=userLoginService.checkEmail(userLogin.getEmail(),userLogin.getId());
                                if(list!=null&&!list.isEmpty()){
                                    respBody.setCode(RespBody.BUSINESS_ERROR);
                                    respBody.setMsg("已存在");
                                    return respBody;
                                }
                                list=userLoginService.checkQqOpenId(userLogin.getQqOpenId(),userLogin.getId());
                                if(list!=null&&!list.isEmpty()){
                                    respBody.setCode(RespBody.BUSINESS_ERROR);
                                    respBody.setMsg("已存在");
                                    return respBody;
                                }
                                list=userLoginService.checkAccount(userLogin.getAccount(),userLogin.getId());
                                if(list!=null&&!list.isEmpty()){
                                    respBody.setCode(RespBody.BUSINESS_ERROR);
                                    respBody.setMsg("已存在");
                                    return respBody;
                                }
        userLogin.setUpBy(currLoginUser().getId());
        userLoginService.update(userLogin);
        respBody.setMsg("更新用户登录信息成功");
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
            UserLogin userLogin=userLoginService.one(Long.parseLong(id));
            userLogin.setDeAt(new Date());
            userLogin.setDeBy(currLoginUser().getId());
            userLoginService.updateTplById(userLogin);
        }
        respBody.setMsg("删除用户登录信息成功");
        return respBody;
    }

    /**
    * 详细
    *
    * @param id
    * @return
    */
    @PostMapping("/view/{id}")
    public UserLogin view(@PathVariable("id") Long id) {
        UserLogin userLogin=userLoginService.one("userLogin.sample",UserLogin.builder().id(id).build());
        return userLogin;
    }

}
