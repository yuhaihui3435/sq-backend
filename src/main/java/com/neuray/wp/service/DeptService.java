////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.Dept;
import com.neuray.wp.entity.DeptJson;
import com.neuray.wp.entity.SysMenuRight;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DeptService extends BaseService<Dept> {


    /**
     * 向上查询关系链
     *
     * @param parentId :
     * @return : java.lang.Long[]
     */
    public String findAllParentId(Long parentId) {
        if (parentId == null) {
            return "";
        } else {
            Dept dept = this.one(parentId);
            if (StringUtils.isNotBlank(findAllParentId(dept.getParentId()))) {
                String ids = findAllParentId(dept.getParentId()) + "," + parentId;
                return ids;
            } else {
                return parentId + "";
            }
        }
    }

    /**
     * 查询所有机构
     *
     * @return : java.util.List<com.neuray.wp.entity.Dept>
     */
    public List<Dept> listAll() {
        return this.sqlManager.lambdaQuery(Dept.class).andIsNull(Dept::getDeAt).orderBy(" cr_at asc ").select();
    }

    /**
     * 检查机构编号是否存在
     *
     * @param deptCode
     * @param id
     * @return
     */
    public List<Dept> checkDeptCode(String deptCode, Long id) {
        if (id == null)
            return this.sqlManager.lambdaQuery(Dept.class).andEq(Dept::getDeptCode, deptCode).andIsNull(Dept::getDeAt).select();
        else
            return this.sqlManager.lambdaQuery(Dept.class).andNotEq(Dept::getId, id).andEq(Dept::getDeptCode, deptCode).andIsNull(Dept::getDeAt).select();
    }

    /**
     * 获取树结构
     *
     * @param allOrgs :
     * @return : java.util.List<com.neuray.wp.entity.TreeJson>
     */
    public List<DeptJson> getTreeOrgs(List<DeptJson> allOrgs) throws Exception {
        List<DeptJson> listParentRecord = new ArrayList<DeptJson>();
        List<DeptJson> listNotParentRecord = new ArrayList<DeptJson>();
        // 第一步：遍历allOrgs找出所有的根节点和非根节点
        if (allOrgs != null && allOrgs.size() > 0) {
            for (DeptJson org : allOrgs) {
                if (null == org.getParentId()) {
                    listParentRecord.add(org);
                } else {
                    listNotParentRecord.add(org);
                }
            }
        }
        // 第二步： 递归获取所有子节点
        if (listParentRecord.size() > 0) {
            for (DeptJson record : listParentRecord) {
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
    public List<DeptJson> getChildOrgs(List<DeptJson> childList, Long parentId) throws Exception {
        List<DeptJson> listParentOrgs = new ArrayList<DeptJson>();
        List<DeptJson> listNotParentOrgs = new ArrayList<DeptJson>();
        // 遍历childList，找出所有的根节点和非根节点
        if (childList != null && childList.size() > 0) {
            for (DeptJson record : childList) {
                // 对比找出父节点
                if (record.getParentId() == parentId) {
                    listParentOrgs.add(record);
                } else {
                    listNotParentOrgs.add(record);
                }
            }
        }
        // 查询子节点
        if (listParentOrgs.size() > 0) {
            for (DeptJson record : listParentOrgs) {
                // 递归查询子节点
                record.setListChild(getChildOrgs(listNotParentOrgs, record.getId()));
            }
        }
        return listParentOrgs;
    }

}