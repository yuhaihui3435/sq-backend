////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service;

import cn.hutool.core.util.StrUtil;
import com.neuray.wp.Consts;
import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.SysUser;
import org.beetl.sql.core.query.LambdaQuery;
import org.beetl.sql.core.query.Query;
import org.beetl.sql.core.query.QueryCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SysUserService extends BaseService<SysUser> {
    /**
     * 检查EMAIL是否存在
     *
     * @param email
     * @param id
     * @return
     */
    public List<SysUser> checkEmail(String email, Long id) {
        if (id == null) {
            if (StrUtil.isNotBlank(email))
                return this.sqlManager.lambdaQuery(SysUser.class).andEq(SysUser::getEmail, email).andIsNull(SysUser::getDeAt).select();
        } else {
            if (StrUtil.isNotBlank(email))
                return this.sqlManager.lambdaQuery(SysUser.class).andNotEq(SysUser::getId, id).andEq(SysUser::getEmail, email).andIsNull(SysUser::getDeAt).select();
        }
        return null;

    }

    /**
     * 检查电话是否存在
     *
     * @param tel
     * @param id
     * @return
     */
    public List<SysUser> checkTel(String tel, Long id) {
        if (id == null) {
            if (StrUtil.isNotBlank(tel))
                return this.sqlManager.lambdaQuery(SysUser.class).andEq(SysUser::getTel, tel).andIsNull(SysUser::getDeAt).select();
        } else {
            if (StrUtil.isNotBlank(tel))
                return this.sqlManager.lambdaQuery(SysUser.class).andNotEq(SysUser::getId, id).andEq(SysUser::getTel, tel).andIsNull(SysUser::getDeAt).select();
        }
        return null;
    }

    /**
     * 检查用户编号是否存在
     *
     * @param suCode
     * @param id
     * @return
     */
    public List<SysUser> checkSuCode(String suCode, Long id) {
        if (id == null) {
            if (StrUtil.isNotBlank(suCode))
                return this.sqlManager.lambdaQuery(SysUser.class).andEq(SysUser::getSuCode, suCode).andIsNull(SysUser::getDeAt).select();
        } else {
            if (StrUtil.isNotBlank(suCode))
                return this.sqlManager.lambdaQuery(SysUser.class).andNotEq(SysUser::getId, id).andEq(SysUser::getSuCode, suCode).andIsNull(SysUser::getDeAt).select();
        }
        return null;
    }

    /**
     * 根据登录账号或者电话号或者email查找用户信息
     * @param str
     * @return
     */
    public SysUser findBySuCodeOrTelOrEmail(String str){

        LambdaQuery<SysUser> query=this.sqlManager.lambdaQuery(SysUser.class);
        List<SysUser> sysUsers=query.andIsNull(SysUser::getDeAt).and(query.condition().orEq(SysUser::getSuCode,str).orEq(SysUser::getTel,str).orEq(SysUser::getEmail,str)
        ).andEq(SysUser::getStatus, Consts.STATUS.AVAILABLE.getCode()).select();
        return sysUsers.size()==1?sysUsers.get(0):null;
    }

}