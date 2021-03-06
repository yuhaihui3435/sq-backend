////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2019. 东睿科技有限公司.保留所有权利
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.controller.www;

import com.neuray.wp.entity.doctor.Doctor;
import com.neuray.wp.service.doctor.DoctorService;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;

/**
 * @Description 基础数据
 * @Param
 * @Author zzq
 * @Date 2019/7/21 15:37
 * @return
 **/
@RestController
@RequestMapping("/api/doctor")
public class ApiDoctorController {

    @Autowired
    private DoctorService doctorService;

    /**
     * 分页
     *
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
        pageQuery = doctorService.page("doctor.doctor.sampleApi", pageQuery);
        return pageQuery;
    }

    /**
     * 详细
     *
     * @return
     */
    @PostMapping("/view")
    public Doctor view(@RequestBody Doctor condition) {
        Doctor doctor = doctorService.one("doctor.doctor.sample", condition);
        return doctor;
    }

}
