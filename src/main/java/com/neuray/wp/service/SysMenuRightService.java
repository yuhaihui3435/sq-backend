////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service;

import cn.hutool.core.util.ArrayUtil;
import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.SysMenuRight;
import com.neuray.wp.entity.SysMenuRightJson;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysMenuRightService extends BaseService<SysMenuRight> {

    /**
     * 查询所有菜单
     *
     * @return : java.util.List<com.neuray.wp.entity.SysMenuRight>
     */
    public List<SysMenuRight> listAll() {
        return this.sqlManager.lambdaQuery(SysMenuRight.class).andIsNull(SysMenuRight::getDeAt).orderBy(" cr_at asc ").select();
    }

    /**
     * 检查菜单代码是否存在
     *
     * @param smCode
     * @param id
     * @return
     */
    public List<SysMenuRight> checkSmCode(String smCode, Long id) {
        if (id == null)
            return this.sqlManager.lambdaQuery(SysMenuRight.class).andEq(SysMenuRight::getSmCode, smCode).andIsNull(SysMenuRight::getDeAt).select();
        else
            return this.sqlManager.lambdaQuery(SysMenuRight.class).andNotEq(SysMenuRight::getId, id).andEq(SysMenuRight::getSmCode, smCode).andIsNull(SysMenuRight::getDeAt).select();
    }


    /**
     * 获取树结构
     *
     * @param allOrgs :
     * @return : java.util.List<com.neuray.wp.entity.TreeJson>
     */
    public List<SysMenuRightJson> getTreeOrgs(List<SysMenuRightJson> allOrgs) throws Exception {
        List<SysMenuRightJson> listParentRecord = new ArrayList<SysMenuRightJson>();
        List<SysMenuRightJson> listNotParentRecord = new ArrayList<SysMenuRightJson>();
        // 第一步：遍历allOrgs找出所有的根节点和非根节点
        if (allOrgs != null && allOrgs.size() > 0) {
            for (SysMenuRightJson org : allOrgs) {
                if (null == org.getParentId()) {
                    listParentRecord.add(org);
                } else {
                    listNotParentRecord.add(org);
                }
            }
        }
        // 第二步： 递归获取所有子节点
        if (listParentRecord.size() > 0) {
            for (SysMenuRightJson record : listParentRecord) {
                // 添加所有子级
                record.setListChild(this.getChildOrgs(listNotParentRecord, record.getId()));
            }
        }
        return listParentRecord;
    }

    /**
     * 子节点
     *
     * @param childList :
     * @param parentId  :
     * @return : java.util.List<com.neuray.wp.entity.TreeJson>
     */
    public List<SysMenuRightJson> getChildOrgs(List<SysMenuRightJson> childList, Long parentId) throws Exception {
        List<SysMenuRightJson> listParentOrgs = new ArrayList<SysMenuRightJson>();
        List<SysMenuRightJson> listNotParentOrgs = new ArrayList<SysMenuRightJson>();
        // 遍历childList，找出所有的根节点和非根节点
        if (childList != null && childList.size() > 0) {
            for (SysMenuRightJson record : childList) {
                // 对比找出父节点
                if (record.getParentId() .equals( parentId)) {
                    listParentOrgs.add(record);
                } else {
                    listNotParentOrgs.add(record);
                }
            }
        }
        // 查询子节点
        if (listParentOrgs.size() > 0) {
            for (SysMenuRightJson record : listParentOrgs) {
                // 递归查询子节点
                record.setListChild(getChildOrgs(listNotParentOrgs, record.getId()));
            }
        }
        return listParentOrgs;
    }

    /**
     * 查询角色拥有的所有菜单集合
     *
     * @param roleId :
     * @return : java.util.List<com.neuray.wp.entity.SysMenuRight>
     */
    public List<SysMenuRight> queryMenuByRole(String roleId) {
        Map map = new HashMap<String, Object>();
        map.put("ids", roleId.split(","));
        return this.manyWithMap("sysMenuRight.queryMenuByRole", map);
    }

    /**
     * 根据用户id查询菜单对象
     *
     * @param userId :
     * @return : java.util.List<com.neuray.wp.entity.SysMenuRight>
     */
    public List<SysMenuRight> queryMenuByUser(String userId) {
        Map map = new HashMap<String, Object>();
        map.put("userId", userId);
        return this.manyWithMap("sysMenuRight.queryMenuByUser", map);
    }

}