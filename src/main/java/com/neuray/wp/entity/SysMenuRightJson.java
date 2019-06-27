package com.neuray.wp.entity;

import java.util.List;

/**
 * 树josn实体类
 * 时间 2019/1/14
 *
 * @author zzq
 * @version v1.0
 * @see
 * @since
 */
public class SysMenuRightJson extends SysMenuRight {

    private List<SysMenuRightJson> listChild;

    public List<SysMenuRightJson> getListChild() {
        return listChild;
    }

    public void setListChild(List<SysMenuRightJson> listChild) {
        this.listChild = listChild;
    }
}
