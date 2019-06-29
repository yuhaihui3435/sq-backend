////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity.artice;


import com.neuray.wp.core.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/*
 * 文章
 * gen by xtf 2019-06-27
 *
 */
@Data
@Builder
@Table(name = "ARTICE_T")
public class Artice extends BaseEntity {
    @Tolerate
    public Artice() {
    }

    @AutoID
    private Long id;

    private Long readingTime;

    @NotBlank(message = "必填")
    private Date effect;

    @NotBlank(message = "必填")
    private Long columnId;

    @NotBlank(message = "必填")
    private String tplName;

    public String getTplNameStr() {
        String ret = null;
        return ret;
    }

    @NotBlank(message = "必填")
    private String title;

    private Long author;

    private Date expired;

    private Long upBy;

    @NotBlank(message = "必填")
    private String summaryEn;

    @NotBlank(message = "必填")
    private String detail;

    @NotBlank(message = "必填")
    private String publishStatus;

    public String getPublishStatusStr() {
        String ret = null;
        return ret;
    }

    private Date deAt;

    private String coverPic;

    private String reportStatus;

    public String getReportStatusStr() {
        String ret = null;
        return ret;
    }

    private Long crBy;

    private Date crAt;

    private Long deBy;

    private Date upAt;

    @NotBlank(message = "必填")
    private Integer top;

    public String getTopStr() {
        String ret = null;
        return ret;
    }

    @NotBlank(message = "必填")
    private String leaveMsgStatus;

    public String getLeaveMsgStatusStr() {
        String ret = null;
        return ret;
    }

    @NotBlank(message = "必填")
    private String titleEn;

    @NotBlank(message = "必填")
    private String detailEn;

    private String summary;

}