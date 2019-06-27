////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.RoleWidget;
import com.neuray.wp.entity.SysMenuRight;
import com.neuray.wp.entity.UserWidget;
import com.neuray.wp.entity.Widget;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WidgetService extends BaseService<Widget> {
    /**
     * 检查编号是否存在
     *
     * @param code
     * @param id
     * @return
     */
    public List<Widget> checkCode(String code, Long id) {
        if (id == null)
            return this.sqlManager.lambdaQuery(Widget.class).andEq(Widget::getCode, code).andIsNull(Widget::getDeAt).select();
        else
            return this.sqlManager.lambdaQuery(Widget.class).andNotEq(Widget::getId, id).andEq(Widget::getCode, code).andIsNull(Widget::getDeAt).select();
    }

    /**
     * 根据角色id查询所有组件对象
     *
     * @param roleId :
     * @return : java.util.List<com.neuray.wp.entity.Widget>
     */
    public List<Widget> queryWidgetByRole(String roleId) {
        Map map = new HashMap<String, Object>();
        map.put("ids", roleId.split(","));
        return this.manyWithMap("widget.queryWidgetByRole", map);
    }

    /**
     * 根据角色查询所有组件对象（只包含组件对象，去重）
     *
     * @param roleId :
     * @return : java.util.List<com.neuray.wp.entity.Widget>
     */
    public List<Widget> queryWidgetByRoleDis(String roleId) {
        Map map = new HashMap<String, Object>();
        map.put("ids", roleId.split(","));
        return this.manyWithMap("widget.queryWidgetByRoleDis", map);
    }

    /**
     * 根据用户id查询小组件
     *
     * @param userId :
     * @return : java.util.List<com.neuray.wp.entity.Widget>
     */
    public List<Widget> queryWidgetByUser(String userId) {
        Map map = new HashMap<String, Object>();
        map.put("userId", userId);
        return this.manyWithMap("widget.queryWidgetByUser", map);
    }

}