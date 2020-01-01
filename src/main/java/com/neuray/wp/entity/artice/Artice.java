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
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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

    private Long readingTime;//读取时间


    private Date effect;//有效时间

    @NotNull(message = "栏目必填")
    private Long columnId;//栏目id


    private String tplName;

    public String getTplNameStr() {
        String ret = null;
        return ret;
    }

    @NotBlank(message = "标题必填")
    private String title;

    private String author;

    private Date expired;

    private Long upBy;


    private String summaryEn;

    @NotBlank(message = "内容必填")
    private String detail;

    @NotBlank(message = "发布状态必填")
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


    private Integer top;

    public String getTopStr() {
        String ret = null;
        return ret;
    }


    private String leaveMsgStatus;

    public String getLeaveMsgStatusStr() {
        String ret = null;
        return ret;
    }


    private String titleEn;


    private String detailEn;

    private String summary;

    private List<Long> tagId;

    private List<Long> columnIds;

    private String origin;//来源

    private String topShow;

    private String language;

    private String bigCoverPic;

}