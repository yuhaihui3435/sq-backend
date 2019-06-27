////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.SysConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
/**
 *
 * 时间 2019/6/24
 * @author 小听风
 * @version v1.0
 * @see
 * @since
 */
public class SysConfService extends BaseService<SysConf> {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 根据key查询单个对象
     *
     * @param key :
     * @return : com.neuray.wp.entity.SysConf
     */
    public SysConf findByKey(String key) {
        return this.tplOne(SysConf.builder().scKey(key).build());
    }

    /**
     * 检查配置名称是否存在
     *
     * @param scName
     * @param id
     * @return
     */
    public List<SysConf> checkScName(String scName, Long id) {
        if (id == null)
            return this.sqlManager.lambdaQuery(SysConf.class).andEq(SysConf::getScName, scName).select();
        else
            return this.sqlManager.lambdaQuery(SysConf.class).andNotEq(SysConf::getId, id).andEq(SysConf::getScName, scName).select();
    }

    /**
     * 检查配置KEY是否存在
     *
     * @param scKey
     * @param id
     * @return
     */
    public List<SysConf> checkScKey(String scKey, Long id) {
        if (id == null)
            return this.sqlManager.lambdaQuery(SysConf.class).andEq(SysConf::getScKey, scKey).select();
        else
            return this.sqlManager.lambdaQuery(SysConf.class).andNotEq(SysConf::getId, id).andEq(SysConf::getScKey, scKey).select();
    }

    public  void test(){
        sysUserService.one(sysRoleService.one(81L).getCrBy());
    }


}