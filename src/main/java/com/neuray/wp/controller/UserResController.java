////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller;


import cn.hutool.core.util.StrUtil;
import com.neuray.wp.Consts;
import com.neuray.wp.annotation.LogDog;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.entity.SysMenuRight;
import com.neuray.wp.entity.SysResRight;
import com.neuray.wp.entity.UserMenu;
import com.neuray.wp.entity.UserRes;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.service.SysResRightService;
import com.neuray.wp.service.UserResService;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.kits.ValidationKit;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/userRes")
@Slf4j
public class UserResController extends BaseController {

    @Autowired
    private UserResService userResService;
    @Autowired
    private SysResRightService sysResRightService;

    /**
     * 分页
     *
     * @param condition
     * @return
     */
//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/page")
    public PageQuery page(@RequestBody UserRes condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = userResService.page("userRes.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param userRes
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.SAVE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/save")
    public RespBody save(@RequestBody UserRes userRes) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(userRes);
        userRes.setCrBy(currLoginUser().getId());
        userResService.insertAutoKey(userRes);
        respBody.setMsg("新增用户资源关系成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param userRes
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/update")
    public RespBody update(@RequestBody UserRes userRes) {

        RespBody respBody = new RespBody();
        ValidationKit.validate(userRes);
        userRes.setUpBy(currLoginUser().getId());
        userResService.updateTplById(userRes);
        respBody.setMsg("更新用户资源关系成功");
        return respBody;
    }

    /**
     * 删除
     *
     * @param param
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.LOGICDEL, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/del")
    public RespBody del(@RequestBody Map<String, String> param) {
        RespBody respBody = new RespBody();
        if (StrUtil.isBlank(param.get("ids"))) throw new LogicException("删除操作失败，缺少删除数据");
        String[] idArray = StrUtil.split(param.get("ids"), ",");
        for (String id : idArray) {
            UserRes userRes = userResService.one(Long.parseLong(id));
            userRes.setDeAt(new Date());
            userRes.setDeBy(currLoginUser().getId());
            userResService.updateTplById(userRes);
        }
        respBody.setMsg("删除用户资源关系成功");
        return respBody;
    }

    /**
     * 详细
     *
     * @param id
     * @return
     */
//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/view/{id}")
    public UserRes view(@PathVariable("id") Long id) {
        UserRes userRes = userResService.one("userRes.sample", UserRes.builder().id(id).build());
        return userRes;
    }

    /**
     * 根据用户id查询资源id
     *
     * @param param :
     * @return : java.lang.Long[]
     */
//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/queryResByUser")
    public Long[] queryResByUser(@RequestBody Map<String, String> param) {
        List<SysResRight> list = new ArrayList<SysResRight>();
        list = sysResRightService.queryResByUser(param.get("userId"));
        Long[] l = new Long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            l[i] = list.get(i).getId();
        }
        return l;
    }

    /**
     * 用户资源时间配置
     *
     * @param param :
     * @return : com.neuray.wp.core.RespBody
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/settingRes")
    public RespBody settingRes(@RequestBody Map<String, String> param) {
        RespBody respBody = new RespBody();
        //根据用户id查询出所有的用户菜单关系数据，全部删除，再重新添加本次的数据
        List<UserRes> userResList = userResService.queryResByUser(param.get("userId"));
        for (int i = 0; i < userResList.size(); i++) {
            UserRes userRes = userResList.get(i);
            userRes.setDeAt(new Date());
            userRes.setDeBy(currLoginUser().getId());
            userResService.update(userRes);
        }
        String[] idArray = StrUtil.split(param.get("ids"), ",");
        for (int i = 0; i < idArray.length; i++) {
            String userId = param.get("userId");
            //添加
            UserRes userRes = new UserRes();
            userRes.setSysUserId(Long.parseLong(userId));
            userRes.setSysResId(Long.parseLong(idArray[i]));
            userRes.setCrBy(currLoginUser() != null ? currLoginUser().getId() : null);
            userRes.setUpBy(currLoginUser() != null ? currLoginUser().getId() : null);
            userRes.setEffect(new Date());
            userResService.insertAutoKey(userRes);
        }
        respBody.setMsg("操作成功");
        respBody.setCode(RespBody.SUCCESS);
        return respBody;
    }

    /**
     * 根据用户id查询用户资源关系对象
     *
     * @param param :
     * @return : java.util.List<com.neuray.wp.entity.UserRes>
     */
//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/queryUserResByUser")
    public List<UserRes> queryUserResByUser(@RequestBody Map<String, String> param) {
        return userResService.queryResByUser(param.get("userId"));
    }

    /**
     * 配置资源
     *
     * @param map :
     * @return : com.neuray.wp.core.RespBody
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/resTimeSet")
    public RespBody resTimeSet(@RequestBody Map<String, UserRes[]> map) {
        RespBody respBody = new RespBody();
        UserRes[] userRess = map.get("userRess");
        for (int i = 0; i < userRess.length; i++) {
            List<UserRes> list = userResService.listByUserIdAndResId(userRess[i].getSysUserId(), userRess[i].getSysResId());
            if (list.size() > 0) {
                UserRes userRes = list.get(0);
                userRes.setEffect(userRess[i].getEffect());
                userRes.setExpired(userRess[i].getExpired());
                userResService.update(userRes);
            }
        }
        respBody.setCode(RespBody.SUCCESS);
        respBody.setMsg("保存成功");
        return respBody;
    }

}
