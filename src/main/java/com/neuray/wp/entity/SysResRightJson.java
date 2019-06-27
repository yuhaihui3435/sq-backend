package com.neuray.wp.entity;

import java.util.List;

/**
 * 系统资源json类
 * 时间 2019/1/15
 *
 * @author zzq
 * @version v1.0
 * @see
 * @since
 */
public class SysResRightJson extends SysResRight {

    private List<SysResRightJson> listChild;

    public List<SysResRightJson> getListChild() {
        return listChild;
    }

    public void setListChild(List<SysResRightJson> listChild) {
        this.listChild = listChild;
    }
}
