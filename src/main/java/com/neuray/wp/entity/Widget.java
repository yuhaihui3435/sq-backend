////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity;


import com.neuray.wp.Consts;
import com.neuray.wp.core.BaseEntity;
import com.neuray.wp.service.CacheService;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/*
 * 小组件
 * gen by xtf 2019-01-15
 *
 */
@Data
@Builder
@Table(name = "WIDGET_T")
public class Widget extends BaseEntity {



    @Tolerate
    public Widget() {
    }

    //创建人
    private Long crBy;

    //修改时间
    private Date upAt;

    //组件默认宽度
    private Long dftW;

    //状态-00:正常,01:停用
    @NotBlank(message = "状态必填")
    private String status;

    public String getStatusStr() {
        return Consts.STATUS.getVal(this.status);
    }

    //组件默认高度
    private Long defH;

    @AutoID
    private Long id;

    //编号
    @NotBlank(message = "编号必填")
    @Pattern(regexp = "^$|[A-Za-z0-9_\\-]+", message = "编号必须是字母或者数字")
    private String code;

    //类型
    @NotNull(message = "类型必填")
    private Long type;

    public String getTypeStr() {
        DictItem dictItem=getDictItemById(getType());
        return dictItem==null?"":dictItem.getItemName();
    }

    //修改时间
    private Date deAt;

    //标题
    @NotBlank(message = "标题必填")
    private String title;

    //访问路径
    private String url;

    //删除人
    private Long deBy;

    //创建时间
    private Date crAt;

    //说明
    private String remark;

    //更新人
    private Long upBy;

}