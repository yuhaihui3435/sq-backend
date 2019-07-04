////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.doctor;


import cn.hutool.core.util.StrUtil;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.entity.doctor.Doctor;
import com.neuray.wp.core.LogicException;
import com.neuray.wp.service.doctor.DoctorService;
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
@RequestMapping("/doctor")
@Slf4j
public class DoctorController extends BaseController {

    @Autowired
    private DoctorService doctorService;
    /**
         *
         * 条件查询
         * @param condition
         * @return
         */
        @PostMapping("/query")
        public List<Doctor> query(@RequestBody Doctor condition) {
            return doctorService.many("doctor.sample", condition);
        }
    /**
     *
     * 分页
     * @param condition
     * @return
     */
    @PostMapping("/page")
    public PageQuery page(@RequestBody Doctor condition) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNumber(condition.getPage());
        pageQuery.setPageSize(condition.getRows());
        pageQuery.setOrderBy(condition.getOrderBy());
        pageQuery.setParas(condition);
        pageQuery = doctorService.page("doctor.sample", pageQuery);
        return pageQuery;
    }

    /**
     * 新增
     *
     * @param doctor
     * @return
     */
    @PostMapping("/save")
    public RespBody save(@RequestBody Doctor doctor) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(doctor);
        doctor.setCrBy(currLoginUser().getId());
        doctor.setUpBy(currLoginUser().getId());
        doctorService.insertAutoKey(doctor);
        respBody.setMsg("新增医生信息成功");
        return respBody;
    }

    /**
     * 更新
     *
     * @param doctor
     * @return
     */
    @PostMapping("/update")
    public RespBody update(@RequestBody Doctor doctor) {
        RespBody respBody = new RespBody();
        ValidationKit.validate(doctor);
        doctor.setUpBy(currLoginUser().getId());
        doctorService.update(doctor);
        respBody.setMsg("更新医生信息成功");
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
            Doctor doctor=doctorService.one(Long.parseLong(id));
            doctor.setDeAt(new Date());
            doctor.setDeBy(currLoginUser().getId());
            doctorService.updateTplById(doctor);
        }
        respBody.setMsg("删除医生信息成功");
        return respBody;
    }

    /**
    * 详细
    *
    * @param id
    * @return
    */
    @PostMapping("/view/{id}")
    public Doctor view(@PathVariable("id") Long id) {
        Doctor doctor=doctorService.one("doctor.sample",Doctor.builder().id(id).build());
        return doctor;
    }

}
