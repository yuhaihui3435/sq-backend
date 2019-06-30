////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2019. 东睿科技有限公司.保留所有权利
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

/**
 * @author:小听风 创建时间:2019/6/29
 * 版本:v1.0
 * @Description:
 */
@Data
@Builder
@Table(name = "FILE_MAP_T")
public class FileMap  {
    @Tolerate
    public FileMap() {
    }
    @AutoID
    private Long id;

    private String fileId;

    private String path;

}
