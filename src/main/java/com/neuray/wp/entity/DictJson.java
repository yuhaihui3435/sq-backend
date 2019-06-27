package com.neuray.wp.entity;

import java.util.List;

/**
 * 数据字典树json实体
 * 时间 2019/1/7
 *
 * @author zzq
 * @version v1.0
 * @see
 * @since
 */
public class DictJson {

    //主键id
    private Long id;
    //树值
    private String dictVal;
    //树名称
    private String dictName;
    //字典集合
    List<DictJson> listDict;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDictVal() {
        return dictVal;
    }

    public void setDictVal(String dictVal) {
        this.dictVal = dictVal;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public List<DictJson> getListDict() {
        return listDict;
    }

    public void setListDict(List<DictJson> listDict) {
        this.listDict = listDict;
    }
}
