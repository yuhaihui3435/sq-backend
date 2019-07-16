////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service.doctor;

import com.neuray.wp.Consts;
import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.doctor.Doctor;
import com.neuray.wp.entity.user.UserLogin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import cn.hutool.core.util.StrUtil;


@Service
@Transactional(rollbackFor = Exception.class)
public class DoctorService extends BaseService<Doctor> {

    /**
     * 查询首页的咨询师数据
     * @param size
     * @return
     */
    public List<Doctor> findByIndexShow(int size){
        Doctor param=Doctor.builder().userLogin(UserLogin.builder().status(Consts.STATUS.AVAILABLE.getCode()).build()).indexShow(Consts.YESORNO.YES.getVal()).build();
        param.setOrderBy("order by doctor.indexShowSeq asc");
        List<Doctor> ret=sqlManager.select("doctor.doctor.sample",Doctor.class,param,0,size);
        return ret;
    }

}