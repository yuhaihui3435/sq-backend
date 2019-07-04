////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity.doctor;


import com.neuray.wp.core.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import javax.validation.constraints.NotBlank;

/*
 * 医生相册
 * gen by xtf 2019-07-04
 *
 */
@Data
@Builder
@Table(name = "DOCTOR_PIC_T")
public class DoctorPic extends BaseEntity {
    @Tolerate
    public DoctorPic() {
    }

    //分类-00:相册
    @NotBlank(message = "分类必填")
    private String type;

    //扩展名
    @NotBlank(message = "扩展名必填")
    private String ext;

    //来至FILE_MAP_T的ID
    @NotBlank(message = "来至FILE_MAP_T的ID必填")
    private Long picId;

    @NotBlank(message = "必填")
    private Long doctorId;

    @AssignID
    private Long id;

    //说明
    private String describe;

}