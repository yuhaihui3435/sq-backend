////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service.user;

import cn.hutool.core.util.StrUtil;
import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.user.UserLogin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
/**
 * 
 * 时间 2019/6/27
 * @author 小听风  
 * @version v1.0
 * @see 
 * @since
 */
public class UserLoginService extends BaseService<UserLogin> {
    /**
     * 检查是否存在
     *
     * @param wxOpenId
     * @param id
     * @return
     */
    public List<UserLogin> checkWxOpenId(String wxOpenId, Long id) {
        if (id == null) {
            if (StrUtil.isNotBlank(wxOpenId)) {
                return this.sqlManager.lambdaQuery(UserLogin.class).andEq(UserLogin::getWxOpenId, wxOpenId).andIsNull(UserLogin::getDeAt).select();
            }

        } else {
            if (StrUtil.isNotBlank(wxOpenId)) {
                return this.sqlManager.lambdaQuery(UserLogin.class).andNotEq(UserLogin::getId, id).andEq(UserLogin::getWxOpenId, wxOpenId).andIsNull(UserLogin::getDeAt).select();
            }
        }
        return null;
    }

    /**
     * 检查是否存在
     *
     * @param phone
     * @param id
     * @return
     */
    public List<UserLogin> checkPhone(String phone, Long id) {
        if (id == null) {
            if (StrUtil.isNotBlank(phone)) {
                return this.sqlManager.lambdaQuery(UserLogin.class).andEq(UserLogin::getPhone, phone).andIsNull(UserLogin::getDeAt).select();
            }

        } else {
            if (StrUtil.isNotBlank(phone)) {
                return this.sqlManager.lambdaQuery(UserLogin.class).andNotEq(UserLogin::getId, id).andEq(UserLogin::getPhone, phone).andIsNull(UserLogin::getDeAt).select();
            }
        }
        return null;
    }

    /**
     * 检查是否存在
     *
     * @param email
     * @param id
     * @return
     */
    public List<UserLogin> checkEmail(String email, Long id) {
        if (id == null) {
            if (StrUtil.isNotBlank(email)) {
                return this.sqlManager.lambdaQuery(UserLogin.class).andEq(UserLogin::getEmail, email).andIsNull(UserLogin::getDeAt).select();
            }

        } else {
            if (StrUtil.isNotBlank(email)) {
                return this.sqlManager.lambdaQuery(UserLogin.class).andNotEq(UserLogin::getId, id).andEq(UserLogin::getEmail, email).andIsNull(UserLogin::getDeAt).select();
            }
        }
        return null;
    }

    /**
     * 检查是否存在
     *
     * @param qqOpenId
     * @param id
     * @return
     */
    public List<UserLogin> checkQqOpenId(String qqOpenId, Long id) {
        if (id == null) {
            if (StrUtil.isNotBlank(qqOpenId)) {
                return this.sqlManager.lambdaQuery(UserLogin.class).andEq(UserLogin::getQqOpenId, qqOpenId).andIsNull(UserLogin::getDeAt).select();
            }

        } else {
            if (StrUtil.isNotBlank(qqOpenId)) {
                return this.sqlManager.lambdaQuery(UserLogin.class).andNotEq(UserLogin::getId, id).andEq(UserLogin::getQqOpenId, qqOpenId).andIsNull(UserLogin::getDeAt).select();
            }
        }
        return null;
    }

    /**
     * 检查是否存在
     *
     * @param account
     * @param id
     * @return
     */
    public List<UserLogin> checkAccount(String account, Long id) {
        if (id == null) {
            if (StrUtil.isNotBlank(account)) {
                return this.sqlManager.lambdaQuery(UserLogin.class).andEq(UserLogin::getAccount, account).andIsNull(UserLogin::getDeAt).select();
            }

        } else {
            if (StrUtil.isNotBlank(account)) {
                return this.sqlManager.lambdaQuery(UserLogin.class).andNotEq(UserLogin::getId, id).andEq(UserLogin::getAccount, account).andIsNull(UserLogin::getDeAt).select();
            }
        }
        return null;
    }


}