////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity.doctor;


import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;
import com.neuray.wp.core.BaseEntity;
import com.neuray.wp.Consts;

import java.util.*;
import javax.validation.constraints.*;

import org.beetl.sql.core.annotatoin.AssignID;

/*
 * 医生信息
 * gen by xtf 2019-07-04
 *
 */
@Data
@Builder
@Table(name = "DOCTOR_T")
public class Doctor extends BaseEntity {
    @Tolerate
    public Doctor() {
    }

    //地址
    @NotBlank(message = "地址必填")
    private String site;

    //创建时间
    private Date crAt;

    //个人简介
    @NotBlank(message = "个人简介必填")
    private String introduction;

    //预约须知
    private String notice;

    //登录ID
    private Long loginId;

    //给来访者
    private String forVisitors;

    //市
    @NotBlank(message = "市必填")
    private String city;

    //区
    @NotBlank(message = "区必填")
    private String area;

    //级别
    @NotNull(message = "级别必填")
    private Integer level;

    //省
    @NotBlank(message = "省必填")
    private String province;

    @AutoID
    private Long id;

    //删除人
    private Long deBy;

    //图像
    @NotBlank(message = "图像必填")
    private String avatar;

    //修改时间
    private Date upAt;

    //修改时间
    private Date deAt;

    //创建人
    private Long crBy;

    //姓名
    @NotBlank(message = "姓名必填")
    private String name;

    //咨询经验
    @NotNull(message = "咨询经验必填")
    private Integer duration;

    //更新人
    private Long upBy;

    //咨询价格
    @NotNull(message = "咨询价格必填")
    private Long price;

    //首页显示-0:是,1:否
//    @NotBlank(message = "首页显示必填")
    private String indexShow;

    //首页显示顺序
    private Integer indexShowSeq;

    private String emial;
    private String phone;
    private List<String> tagId;
    private List<String> doctorPicture;

}