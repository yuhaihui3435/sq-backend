////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity.doctor;


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
* 医生标签
* gen by xtf 2019-07-04
*
*/
@Data
@Builder
@Table(name="DOCTOR_TAG_T")
public class DoctorTag extends BaseEntity{
    @Tolerate
    public DoctorTag(){}

        private Long tagId ;

        //标签类型-00:领域,01可预约时间,02:针对群体,03:咨询方式,04:性别
        private String type ;

            public String getTypeStr(){
                String ret=null;
                return ret;
            }

        private Long doctorId ;

                  @AssignID
                  private Long id;

}