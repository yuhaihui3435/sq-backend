////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.entity.artice;


import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neuray.wp.core.BaseEntity;
import com.neuray.wp.kits.SpringKit;
import com.neuray.wp.service.CacheService;
import com.neuray.wp.service.artice.ColumnService;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import javax.validation.constraints.NotBlank;
import java.util.*;

/*
 * 栏目
 * gen by xtf 2019-06-27
 *
 */
@Data
@Builder
@Table(name = "COLUMN_T")
public class Column extends BaseEntity {

    @Tolerate
    public Column(){}

    private Long deBy;

    @AutoID
    private Long id;

    private Long crBy;

    private Date upAt;

    private Date deAt;

    private Date crAt;

    private Long upBy;

    @NotBlank(message = "必填")
    private String name;

    private Long parentId;

    private Integer order;//顺序

    private String keys;//关键字 ，

    private String describe;//描述

    private String thumbnail;//缩略图

    private String mgtStyle;//管理方式  列表，封面

    private String listTpl;//列表模板

    private String detailTpl;//详细模板


    private List<Column> children;

    private Column parent;

//    public List<Map> getChildren(){
//        List<Column> list= columnService.many("artice.column.sample",Column.builder().parentId(this.id).build());
//        List<Map> result=new ArrayList<>();
//
//        list.stream().forEach(column -> {
//            result.add(BeanUtil.beanToMap(column));
//        });
//        return result;
//    }
//
//    public Map getParent(){
//        if(parentId!=null) {
//            Column column = columnService.one(this.parentId);
//            return BeanUtil.beanToMap(column);
//        }else{
//            return null;
//        }
//
//    }

}