////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service.website;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.website.Links;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import cn.hutool.core.util.StrUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class LinksService extends BaseService<Links> {
                    /**
                     * 检查链接名称是否存在
                     *
                     * @param name
                     * @param id
                     * @return
                     */
    public List<Links> checkName(String name,Long id){
            if(id==null){
                if(StrUtil.isNotBlank(name)){
                    return this.sqlManager.lambdaQuery(Links.class).andEq(Links::getName, name).select();
                }

            }else{
                if(StrUtil.isNotBlank(name)){
                    return this.sqlManager.lambdaQuery(Links.class).andNotEq(Links::getId, id).andEq(Links::getName, name).select();
                }
                }
            return null;
    }


}