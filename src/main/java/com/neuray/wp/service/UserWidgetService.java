////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.RoleWidget;
import com.neuray.wp.entity.UserWidget;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserWidgetService extends BaseService<UserWidget> {

    /**
     * @param userId   :
     * @param widgetId :
     * @return : java.util.List<com.neuray.wp.entity.UserWidget>
     */
    public List<UserWidget> listByUserIdAndWidgetId(long userId, long widgetId) {
        return this.sqlManager.lambdaQuery(UserWidget.class).andEq(UserWidget::getSysUserId, userId).andEq(UserWidget::getWidgetId, widgetId).andIsNull(UserWidget::getDeAt).select();
    }

    /**
     * 根据用户id查询用户角色关系
     *
     * @param userId :
     * @return : java.util.List<com.neuray.wp.entity.UserWidget>
     */
    public List<UserWidget> queryUserWidgetByUserId(Long userId) {
        return this.sqlManager.lambdaQuery(UserWidget.class).andEq(UserWidget::getSysUserId, userId).andIsNull(UserWidget::getDeAt).select();
    }

    /**
     * 查找主页的组件查询
     *
     * @param userId :
     * @return : java.util.List<com.neuray.wp.entity.UserWidget>
     */
    public List<UserWidget> queryWidgetLinkByUserId(Long userId) {
        Map map = new HashMap<String, Object>();
        map.put("userId", userId);
        return this.manyWithMap("widget.queryWidgetLinkByUserId", map);
    }

    /**
     * 根据编码和用户id查询用户组件关系对象
     *
     * @param code   :
     * @param userId :
     * @return : java.util.List<com.neuray.wp.entity.UserWidget>
     */
    public List<UserWidget> queryUserWidgetLinkByCode(String code, Long userId) {
        Map map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("userId", userId);
        return this.manyWithMap("userWidget.queryUserWidgetLinkByCode", map);
    }

    /**
     * 根据用户id查询小组件对象
     *
     * @param userId :
     * @return : java.util.List<com.neuray.wp.entity.UserWidget>
     */
    public List<UserWidget> queryUserWidgetByUser(String userId) {
        Map map = new HashMap<String, Object>();
        map.put("userId", userId);
        return this.manyWithMap("widget.queryWidgetByUser", map);
    }

}