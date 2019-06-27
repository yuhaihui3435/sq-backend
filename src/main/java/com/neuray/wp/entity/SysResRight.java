////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity;


import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;
import com.neuray.wp.core.BaseEntity;
import com.neuray.wp.Consts;

import java.util.*;
import javax.validation.constraints.*;

/*
 * 系统资源
 * gen by xtf 2019-01-14
 *
 */
@Data
@Builder
@Table(name = "SYS_RES_RIGHT_T")
public class SysResRight extends BaseEntity {
    @Tolerate
    public SysResRight() {
    }

    //资源编码
    @NotBlank(message = "资源编码必填")
    private String code;

    @AssignID
    private Long id;

    //资源类型
    @NotNull(message = "资源类型必填")
    private Long type;

//    public String getTypeStr(){
//        DictItem dictItem=getDictItemById(getType());
//        return dictItem==null?"":dictItem.getItemName();
//    }

    //资源地址
    @NotBlank(message = "资源地址必填")
    private String resUri;

    //创建时间
    private Date crAt;

    //删除人
    private Long deBy;

    //更新时间
    private Date upAt;

    //创建人
    private Long crBy;

    //删除时间
    private Date deAt;

    //更新人
    private Long upBy;

    //上级资源
//    @NotBlank(message = "上级资源必填")
    private Long parentId;

    //资源名称
    @NotBlank(message = "资源名称必填")
    private String resName;

    //状态-00:正常,01:停用
    @NotBlank(message = "状态必填")
    private String status;

    public String getStatusStr() {
        return Consts.STATUS.getVal(this.status);
    }

}