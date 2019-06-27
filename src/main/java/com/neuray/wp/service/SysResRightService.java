////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.SysMenuRight;
import com.neuray.wp.entity.SysResRight;
import com.neuray.wp.entity.SysResRightJson;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysResRightService extends BaseService<SysResRight> {

    /**
     * 查询所有资源
     *
     * @return : java.util.List<com.neuray.wp.entity.SysMenuRight>
     */
    public List<SysResRight> listAll() {
        return this.sqlManager.lambdaQuery(SysResRight.class).andIsNull(SysResRight::getDeAt).orderBy(" cr_at asc ").select();
    }

    /**
     * 检查资源名称是否存在
     *
     * @param resName
     * @param id
     * @return
     */
    public List<SysResRight> checkResName(String resName, Long id) {
        if (id == null)
            return this.sqlManager.lambdaQuery(SysResRight.class).andEq(SysResRight::getResName, resName).andIsNull(SysResRight::getDeAt).select();
        else
            return this.sqlManager.lambdaQuery(SysResRight.class).andNotEq(SysResRight::getId, id).andEq(SysResRight::getResName, resName).andIsNull(SysResRight::getDeAt).select();
    }

    /**
     * 检查资源编码是否存在
     *
     * @param code
     * @param id
     * @return
     */
    public List<SysResRight> checkCode(String code, Long id) {
        if (id == null)
            return this.sqlManager.lambdaQuery(SysResRight.class).andEq(SysResRight::getCode, code).andIsNull(SysResRight::getDeAt).select();
        else
            return this.sqlManager.lambdaQuery(SysResRight.class).andNotEq(SysResRight::getId, id).andEq(SysResRight::getCode, code).andIsNull(SysResRight::getDeAt).select();
    }

    /**
     * 获取树结构
     *
     * @param allOrgs :
     * @return : java.util.List<com.neuray.wp.entity.TreeJson>
     */
    public List<SysResRightJson> getTreeOrgs(List<SysResRightJson> allOrgs) throws Exception {
        List<SysResRightJson> listParentRecord = new ArrayList<SysResRightJson>();
        List<SysResRightJson> listNotParentRecord = new ArrayList<SysResRightJson>();
        // 第一步：遍历allOrgs找出所有的根节点和非根节点
        if (allOrgs != null && allOrgs.size() > 0) {
            for (SysResRightJson org : allOrgs) {
                if (null == org.getParentId()) {
                    listParentRecord.add(org);
                } else {
                    listNotParentRecord.add(org);
                }
            }
        }
        // 第二步： 递归获取所有子节点
        if (listParentRecord.size() > 0) {
            for (SysResRightJson record : listParentRecord) {
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
    public List<SysResRightJson> getChildOrgs(List<SysResRightJson> childList, Long parentId) throws Exception {
        List<SysResRightJson> listParentOrgs = new ArrayList<SysResRightJson>();
        List<SysResRightJson> listNotParentOrgs = new ArrayList<SysResRightJson>();
        // 遍历childList，找出所有的根节点和非根节点
        if (childList != null && childList.size() > 0) {
            for (SysResRightJson record : childList) {
                // 对比找出父节点
                if (record.getParentId().equals( parentId)) {
                    listParentOrgs.add(record);
                } else {
                    listNotParentOrgs.add(record);
                }
            }
        }
        // 查询子节点
        if (listParentOrgs.size() > 0) {
            for (SysResRightJson record : listParentOrgs) {
                // 递归查询子节点
                record.setListChild(getChildOrgs(listNotParentOrgs, record.getId()));
            }
        }
        return listParentOrgs;
    }

    /**
     * 查询角色拥有的所有资源集合
     *
     * @param roleId :
     * @return : java.util.List<com.neuray.wp.entity.SysMenuRight>
     */
    public List<SysResRight> queryResByRole(String roleId) {

        Map map = new HashMap<String, Object>();
        map.put("ids", roleId.split(","));
        return this.manyWithMap("sysResRight.queryResByRole", map);
    }

    /**
     * 根据用户id查询资源对象
     *
     * @param userId :
     * @return : java.util.List<com.neuray.wp.entity.SysMenuRight>
     */
    public List<SysResRight> queryResByUser(String userId) {
        Map map = new HashMap<String, Object>();
        map.put("userId", userId);
        return this.manyWithMap("sysResRight.queryResByUser", map);
    }

}