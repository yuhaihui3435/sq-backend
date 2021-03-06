////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity;


import com.neuray.wp.core.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

/*
 * 文件映射表
 * gen by xtf 2019-07-04
 *
 */
@Data
@Builder
@Table(name = "FILE_MAP_T")
public class FileMap extends BaseEntity {
    @Tolerate
    public FileMap() {
    }

    @AssignID
    private Long id;

    //文件类型
    private String type;

    //文件ID
    private String fileId;

    //文件存储路径
    private String path;

    //扩展名
    private String ext;

}