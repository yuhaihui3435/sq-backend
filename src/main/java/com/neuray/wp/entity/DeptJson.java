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
public class DeptJson extends Dept {

    private List<DeptJson> listChild;

    public List<DeptJson> getListChild() {
        return listChild;
    }

    public void setListChild(List<DeptJson> listChild) {
        this.listChild = listChild;
    }
}
