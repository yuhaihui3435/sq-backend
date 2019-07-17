////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity.lesson;


import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import org.beetl.sql.core.annotatoin.Table;
import com.neuray.wp.core.BaseEntity;
import com.neuray.wp.Consts;

import java.util.*;
import javax.validation.constraints.*;

import org.beetl.sql.core.annotatoin.AssignID;

/*
 * 课程
 * gen by xtf 2019-07-04
 *
 */
@Data
@Builder
@Table(name = "LESSON_T")
public class Lesson extends BaseEntity {
    @Tolerate
    public Lesson() {
    }

    //创建时间
    private Date crAt;

    //课程名称
    @NotBlank(message = "课程名称必填")
    private String name;

    //状态-正常:00,停止:01
    @NotBlank(message = "状态必填")
    private String status;

    public String getStatusStr() {
        return Consts.STATUS.getVal(this.status);
    }

    //省
    @NotBlank(message = "省必填")
    private String provice;

    //市
    @NotBlank(message = "市必填")
    private String city;

    //区
    @NotBlank(message = "区必填")
    private String area;

    //课程价格
    @NotNull(message = "课程价格必填")
    private Long price;

    @AssignID
    private Long id;

    //删除人
    private Long deBy;

    //课程地点
    @NotBlank(message = "课程地点必填")
    private String site;

    //修改时间
    private Date upAt;

    //课程说明
    private String describle;

    //修改时间
    private Date deAt;

    //创建人
    private Long crBy;

    //宣传广告图片
    private String publicize;

    //更新人
    private Long upBy;

    //课程时间
    @NotNull(message = "课程时间必填")
    private Date lessonAt;

    //课程结束时间
    @NotNull(message = "课程结束时间必填")
    private Date lessonEndAt;

    //课程天数
    @NotNull(message = "课程天数必填")
    private Integer lessonDays;

    //课程状态-00:未开始,1:进行中,2:已结课
    @NotBlank(message = "课程状态必填")
    private String lessonStatus;

    public String getLessonStatusStr() {
        String ret = null;
        return ret;
    }

    //授课方式-00:线下,01:线上
    @NotBlank(message = "授课方式必填")
    private String theWay;

    public String getTheWayStr() {
        String ret = null;
        return ret;
    }

    //授课老师
    @NotNull(message = "授课老师必填")
    private Long doctorId;
    //宣传方式-1:图片,2:视频
//    @NotBlank(message = "宣传方式必填")
    private String publicizeType;

    //首页显示-0:是,1:否
//    @NotBlank(message = "首页显示必填")
    private String indexShow;

    //首页显示顺序
    private Integer indexShowSeq;
    /**
     * 简介
     */
    private String summary;

    //简介
    private String summary;

}