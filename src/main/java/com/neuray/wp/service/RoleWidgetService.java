////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.RoleMenu;
import com.neuray.wp.entity.RoleRes;
import com.neuray.wp.entity.RoleWidget;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoleWidgetService extends BaseService<RoleWidget> {

    public List<RoleWidget> listByRoleIdAndWidgetId(long roleId, long widgetId) {
        return this.sqlManager.lambdaQuery(RoleWidget.class).andEq(RoleWidget::getRoleId, roleId).andEq(RoleWidget::getWidgetId, widgetId).andIsNull(RoleWidget::getDeAt).select();
    }

    /**
     * @param roleId :
     * @return : java.util.List<com.neuray.wp.entity.RoleWidget>
     */
    public List<RoleWidget> queryWidgetObjByRole(String roleId) {
        Map map = new HashMap<String, Object>();
        map.put("ids", roleId.split(","));
        return this.manyWithMap("widget.queryWidgetByRole", map);
    }

    /**
     * 根据角色查询角色组件关系
     *
     * @param roleId :
     * @return : java.util.List<com.neuray.wp.entity.RoleWidget>
     */
    public List<RoleWidget> queryRoleWidgetByRole(long roleId) {
        return this.sqlManager.lambdaQuery(RoleWidget.class).andEq(RoleWidget::getRoleId, roleId).andIsNull(RoleWidget::getDeAt).select();
    }

}