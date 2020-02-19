////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service.artice;

import cn.hutool.core.util.StrUtil;
import com.neuray.wp.Consts;
import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.artice.Artice;
import org.beetl.sql.core.query.LambdaQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ArticeService extends BaseService<Artice> {
    private final String[] cols={"ID","TITLE","CR_AT","UP_AT","SUMMARY","COVER_PIC"};
    /**
     * 检查是否存在
     *
     * @param title
     * @param id
     * @return
     */
    public List<Artice> checkTitle(String title, Long id) {
        if (id == null) {
            if (StrUtil.isNotBlank(title)) {
                return this.sqlManager.lambdaQuery(Artice.class).andEq(Artice::getTitle, title).andIsNull(Artice::getDeAt).select();
            }

        } else {
            if (StrUtil.isNotBlank(title)) {
                return this.sqlManager.lambdaQuery(Artice.class).andNotEq(Artice::getId, id).andEq(Artice::getTitle, title).andIsNull(Artice::getDeAt).select();
            }
        }
        return null;
    }

    /**
     * 检查是否存在
     *
     * @param titleEn
     * @param id
     * @return
     */
    public List<Artice> checkTitleEn(String titleEn, Long id) {
        if (id == null) {
            if (StrUtil.isNotBlank(titleEn)) {
                return this.sqlManager.lambdaQuery(Artice.class).andEq(Artice::getTitleEn, titleEn).andIsNull(Artice::getDeAt).select();
            }

        } else {
            if (StrUtil.isNotBlank(titleEn)) {
                return this.sqlManager.lambdaQuery(Artice.class).andNotEq(Artice::getId, id).andEq(Artice::getTitleEn, titleEn).andIsNull(Artice::getDeAt).select();
            }
        }
        return null;
    }

    /**
     * 查询首页显示的文章
     * @param size
     * @return
     */
    public List<Artice> findByTopShow(int size,Long columnId){


        if(columnId==null) {
            return sqlManager.lambdaQuery(Artice.class).andEq(Artice::getPublishStatus, Consts.STATUS.AVAILABLE.getCode()).andIsNull(Artice::getDeAt).desc(Artice::getUpAt).limit(0, size).select(cols);
        }else{
            return sqlManager.lambdaQuery(Artice.class).andEq(Artice::getColumnId,columnId).andEq(Artice::getPublishStatus, Consts.STATUS.AVAILABLE.getCode()).andIsNull(Artice::getDeAt).desc(Artice::getUpAt).limit(0, size).select(cols);
        }
    }
    /**
     * 推荐查询，按照更新时间倒序查询
     * @param len
     * @param columnId
     * @return
     */
    public List<Artice> findRecommend(int len,Long columnId,Long articleId){
        len=len==0?5:len;
        if(columnId==null){
            return sqlManager.lambdaQuery(Artice.class).andNotEq(Artice::getId, articleId).andEq(Artice::getPublishStatus, Consts.STATUS.AVAILABLE.getCode()).andIsNull(Artice::getDeAt).desc(Artice::getUpAt).limit(0, len).select(cols);
        }else{
            return sqlManager.lambdaQuery(Artice.class).andNotEq(Artice::getId, articleId).andEq(Artice::getColumnId,columnId).andEq(Artice::getPublishStatus, Consts.STATUS.AVAILABLE.getCode()).andIsNull(Artice::getDeAt).desc(Artice::getUpAt).limit(0, len).select(cols);
        }
    }
    /**
     * 根据顶级栏目，查询叶子栏目下的文章
     * @param size
     * @param columnId
     * @return
     */
    public List<Artice> findByTopColumn(Integer size,Long columnId){
        Map<String,Object> map=new HashMap<>();
        map.put("size", size);
        map.put("columnId", columnId);
        return super.manyWithMap("artice.artice.selectByTopColumn", map);
    }

}