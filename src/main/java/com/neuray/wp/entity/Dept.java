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
import org.springframework.stereotype.Component;

import java.util.*;
import javax.validation.constraints.*;

/*
 * 机构
 * gen by xtf 2019-01-14
 *
 */
@Data
@Builder
@Table(name = "DEPT_T")
public class Dept extends BaseEntity {
    @Tolerate
    public Dept() {
    }

    //上级ID
//    @NotBlank(message = "上级ID必填")
    private Long parentId;

    //机构编号
    @NotBlank(message = "机构编号必填")
    private String deptCode;

    @AssignID
    private Long id;

    //创建时间
    private Date crAt;

    //删除人
    private Long deBy;

    //更新时间
    private Date upAt;

    //顺序号
    @NotNull(message = "顺序号必填")
    private Long qn;

    //创建人
    private Long crBy;

    //删除时间
    private Date deAt;

    //机构名称
    @NotBlank(message = "机构名称必填")
    private String deptName;

    //状态-00:正常,01:停用
    @NotBlank(message = "状态必填")
    private String status;

    public String getStatusStr() {
        return Consts.STATUS.getVal(this.status);
    }

    //更新人
    private Long upBy;

    //关系链
    private String rsChain;

    //组织机构角色
    private String deptRoles;

}