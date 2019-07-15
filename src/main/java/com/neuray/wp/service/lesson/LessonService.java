////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service.lesson;

import com.neuray.wp.Consts;
import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.lesson.Lesson;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import cn.hutool.core.util.StrUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class LessonService extends BaseService<Lesson> {

    /**
     * 查询首页课程,推荐
     * @return
     */
    public List<Lesson> findByIndexShow(int size){
        return sqlManager.lambdaQuery(Lesson.class).andNotEq(Lesson::getLessonStatus,Consts.LESSON_STATUS.FINISHED.getCode()).andEq(Lesson::getIndexShow, Consts.YESORNO.YES.getVal()).andIsNotNull(Lesson::getDeAt).andEq(Lesson::getStatus,Consts.STATUS.AVAILABLE.getCode()).asc(Lesson::getIndexShowSeq).limit(0,size).select();
    }

    public List<Lesson> findByNotIndexShow(int size){
        return sqlManager.lambdaQuery(Lesson.class).andNotEq(Lesson::getLessonStatus,Consts.LESSON_STATUS.FINISHED.getCode()).andEq(Lesson::getIndexShow, Consts.YESORNO.YES.getVal()).andIsNotNull(Lesson::getDeAt).andEq(Lesson::getStatus,Consts.STATUS.AVAILABLE.getCode()).asc(Lesson::getIndexShowSeq).limit(0,size).select();
    }

}