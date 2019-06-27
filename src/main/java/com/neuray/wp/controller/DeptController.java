////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.neuray.wp.Consts;
import com.neuray.wp.annotation.LogDog;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.core.RespBody;
import com.neuray.wp.entity.Dept;
import com.neuray.wp.entity.DeptJson;
import com.neuray.wp.entity.DeptRole;
import com.neuray.wp.kits.ValidationKit;
import com.neuray.wp.model.DeptRoleDto;
import com.neuray.wp.service.DeptRoleService;
import com.neuray.wp.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/dept")
@Slf4j
public class DeptController extends BaseController {

    @Autowired
    private DeptService deptService;
    @Autowired
    private DeptRoleService deptRoleService;

    /**
     * 按条件查询集合
     *
     * @param condition :
     * @return : java.util.List<com.neuray.wp.entity.Dept>
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/query")
    public List<Dept> query(@RequestBody Dept condition) {
        return deptService.many("dept.sample", condition);
    }

    /**
     * 分页
     *
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/page")
    public Map page() throws Exception {
        Map map = new HashMap();
        DeptJson treeJson = new DeptJson();
        treeJson.setDeptName("组织机构");
        treeJson.setId(-1L);
        List<Dept> listDept = deptService.listAll();
        List<DeptJson> listChild = new ArrayList<DeptJson>();
        for (int i = 0; i < listDept.size(); i++) {
            Dept dept = listDept.get(i);
            DeptJson treeJson1 = new DeptJson();
            treeJson1.setId(dept.getId());
            treeJson1.setDeptName(dept.getDeptName());
            treeJson1.setDeptCode(dept.getDeptCode());
            treeJson1.setQn(dept.getQn());
            treeJson1.setParentId(dept.getParentId());
            treeJson1.setCrBy(dept.getCrBy());
            treeJson1.setCrAt(dept.getCrAt());
            treeJson1.setUpBy(dept.getUpBy());
            treeJson1.setUpAt(dept.getUpAt());
            treeJson1.setDeBy(dept.getDeBy());
            treeJson1.setDeAt(dept.getDeAt());
            treeJson1.setStatus(dept.getStatus());
            listChild.add(treeJson1);
        }
        List<DeptJson> listResult = deptService.getTreeOrgs(listChild);
        treeJson.setListChild(listResult);
        String jsonStr = JSONUtil.toJsonStr(treeJson);
        JSONArray jsonArray = JSONUtil.parseArray("[" + jsonStr + "]");
        log.info(jsonArray.toString());
        map.put("jsonArray", jsonArray);
        return map;
    }

    /**
     * 查询部门右侧列表
     *
     * @param condition :
     * @return : org.beetl.sql.core.engine.PageQuery
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/pageItem")
    public PageQuery page(@RequestBody Dept condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setParas(condition);
        pageQuery = deptService.page("dept.special", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param dept
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.SAVE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/save")
    public RespBody save(@RequestBody Dept dept) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(dept);
        List<Dept> list = deptService.checkDeptCode(dept.getDeptCode(), null);
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("机构编号已存在");
            return respBody;
        }
        dept.setCrBy(currLoginUser().getId());
        dept.setUpBy(currLoginUser().getId());
        deptService.insertAutoKey(dept);
        respBody.setMsg("新增机构成功");
        String parentIds = deptService.findAllParentId(dept.getParentId());
        dept.setRsChain(parentIds);
        deptService.update(dept);
        return respBody;
    }

    /**
     * 更新
     *
     * @param dept
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.UPDATE, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/update")
    public RespBody update(@RequestBody Dept dept) {

        RespBody respBody = new RespBody();
        ValidationKit.validate(dept);
        List<Dept> list = deptService.checkDeptCode(dept.getDeptCode(), dept.getId());
        if (!list.isEmpty()) {
            respBody.setCode(RespBody.BUSINESS_ERROR);
            respBody.setMsg("机构编号已存在");
            return respBody;
        }
        dept.setUpBy(currLoginUser().getId());
        String parentIds = deptService.findAllParentId(dept.getParentId());
        dept.setRsChain(parentIds);
        deptService.updateTplById(dept);
        respBody.setMsg("更新机构成功");
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
            Dept dept = deptService.one(Long.parseLong(id));
            dept.setDeAt(new Date());
            dept.setDeBy(currLoginUser().getId());
            deptService.updateTplById(dept);
        }
        respBody.setMsg("删除机构成功");
        return respBody;
    }

    /**
     * 详细
     *
     * @param id
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/view/{id}")
    public Dept view(@PathVariable("id") Long id) {
        Dept dept = deptService.one("dept.sample", Dept.builder().id(id).build());
        return dept;
    }

    /**
     * 查询部门详细，附带查询部门关联关系数据：系统角色信息，
     *
     * @param id
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/oneContainSysRole/{id}")
    public Dept oneContainSysRole(@PathVariable("id") Long id) {
        Dept dept = deptService.one("dept.selectContainRelation", Dept.builder().id(id).build());
        return dept;
    }

    /**
     * 保存部门角色关系设置
     *
     * @param deptRoleDto
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.SAVE, reqSource = Consts.REQSOURCE.INNER)
    @Transactional
    @PostMapping("/saveDeptRoleSet")
    public RespBody saveDeptRoleSet(@RequestBody DeptRoleDto deptRoleDto) {
        RespBody respBody = new RespBody();


        List<DeptRole> deptRoleList = deptRoleService.tpl(DeptRole.builder().deptId(deptRoleDto.getDeptId()).build());
        deptRoleList.stream().forEach(deptRole -> {
            deptRole.setDeAt(new Date());
            deptRole.setDeBy(currLoginUser().getId());
            deptRoleService.update(deptRole);
        });
        for (DeptRole deptRole : deptRoleDto.getDeptRoles()) {
            deptRole.setCrBy(currLoginUser().getId());
            deptRole.setUpBy(currLoginUser().getId());
            deptRole.setEffect(deptRole.getEffect() == null ? new Date() : deptRole.getEffect());
            deptRoleService.insertAutoKey(deptRole);
        }
        respBody.setMsg("角色关系设置成功");
        return respBody;
    }

    /**
     * 根据用户查询出关系部门
     *
     * @param sysUserId
     * @return
     */
    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/queryDeptBySysUserId/{sysUserId}")
    public List<Dept> queryDeptBySysUserId(@PathVariable("sysUserId") Long sysUserId) {
        Map<String, Object> param = new HashMap<>();
        param.put("sysUserId", sysUserId);
        return deptService.manyWithMap("dept.selectBySysUserId", param);
    }

}
