////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.core;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neuray.wp.entity.DictItem;
import com.neuray.wp.kits.SpringKit;
import com.neuray.wp.service.CacheService;
import lombok.Data;
import org.beetl.sql.core.TailBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

@Data
public  class BaseEntity extends TailBean implements Serializable {
    @JsonIgnore
    private transient CacheService cacheService;

    public BaseEntity(){
        cacheService= SpringKit.getBean(CacheService.class);
    }

    private Integer page;
    private Integer rows;
    private String orderBy;

    public Integer getPage(){
        return this.page==null?1:this.page;
    }

    public Integer getRows(){
        return this.rows==null?10:this.rows;
    }

    public String getOrderBy(){
        String alias=this.getClass().getSimpleName();
        alias=StrUtil.lowerFirst(alias)+".";

        return StrUtil.isBlank(this.orderBy)?alias+"UP_AT desc":this.orderBy;
    }
    //解决从reids中读取的的数据，在序列化的时候出现找不到set方法的错误
    public void setTails(Map<String,Object> map){
        this.extMap=map;
    }

    protected DictItem getDictItemById(Long diId){
        return cacheService.getDictItemById(diId);
    }
}
