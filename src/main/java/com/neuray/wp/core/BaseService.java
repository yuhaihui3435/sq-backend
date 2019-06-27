////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.core;

import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.db.KeyHolder;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public abstract class BaseService<T> implements Serializable{

    @Autowired
    protected SQLManager sqlManager;

    public Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 查询全部数据
     * @return
     */
    public List<T> all() {
        return sqlManager.all(getTClass());
    }

    /**
     * 分页查询全部数据
     * @param start
     * @param size
     * @return
     */
    public List<T> all(int start, int size) {
        return sqlManager.all(getTClass(), start, size);
    }

    /**
     * 条件查询全部数据
     * @param entity
     * @return
     */
    public List<T> tpl(T entity) {
        List<T> ret = sqlManager.template(entity);
        return sqlManager.template(entity);
    }

    /**
     * 条件查询单条数据
     * @param entity
     * @param <T>
     * @return
     */
    public <T> T tplOne(T entity) {
        return sqlManager.templateOne(entity);
    }

    /**
     * 分页条件查询
     * @param entity
     * @param start
     * @param size
     * @return
     */
    public List<T> tpl(T entity, int start, int size) {
        return sqlManager.template(entity, start, size);
    }

    /**
     * 统计数量条件查询
     * @param entity
     * @return
     */
    public long tplCount(T entity) {
        return sqlManager.templateCount(entity);
    }

    /**
     * 分页查询
     * @param sqlId
     * @param pageQuery
     * @param <T>
     * @return
     */
    public <T> PageQuery<T> page(String sqlId, PageQuery<T> pageQuery) {
        return sqlManager.pageQuery(sqlId, (Class<T>) getTClass(), pageQuery);
    }

    /**
     * 单条依据sql模板查询
     * @param sqlId
     * @param obj
     * @return
     */
    public T one(String sqlId,Object obj){
        return sqlManager.selectSingle(sqlId,obj, getTClass());
    }

    /**
     * 多条依据sql模板查询，查询条件为entity
     * @param sqlId
     * @param params
     * @return
     */
    public List<T> many(String sqlId,T params){
        return sqlManager.select(sqlId, getTClass(),params);
    }

    /**
     * 多条依据sql模板查询，查询条件为map
     * @param sqlId
     * @param params
     * @return
     */
    public List<T> manyWithMap(String sqlId,Map<String,Object> params){
        return sqlManager.select(sqlId,getTClass(),params);
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    public T one(Object id) {
        return (T) sqlManager.single(getTClass(), id);
    }

    /**
     * 统计所有数据个数
     * @return
     */
    public long count() {
        return sqlManager.allCount(getTClass());
    }

    /**
     * 插入,主键手动维护
     * @param entity
     */
    public void insert(T entity) {
        setCreateTime(entity);
        setUpdateTime(entity);
        sqlManager.insert(entity);
    }

    /**
     * 插入，主键自动维护
     * @param entity
     */
    public void insertAutoKey(T entity) {
        setCreateTime(entity);
        setUpdateTime(entity);
        sqlManager.insert(entity, true);
    }

    /**
     * 条件插入，有值插入，为NULL不插入，主键手动维护
     * @param entity
     */
    public void insertTpl(T entity) {
        setCreateTime(entity);
        setUpdateTime(entity);
        sqlManager.insertTemplate(entity);
    }

    /**
     * 条件插入，有值插入，为NULL不插入，主键自动维护
     * @param entity
     */
    public void insertTplAutoKey(T entity) {
        setCreateTime(entity);
        setUpdateTime(entity);
        sqlManager.insertTemplate(entity, true);
    }

    /**
     * 批量插入
     * @param entitys
     */
    public void insertBatch(List<T> entitys) {
        entitys.stream().forEach(entity -> {setCreateTime(entity);setUpdateTime(entity);});
        sqlManager.insertBatch(getTClass(), entitys);
    }

    /**
     * 根据主键更新
     * @param entity
     * @return
     */
    public int update(T entity) {
        setUpdateTime(entity);
        return sqlManager.updateById(entity);
    }

    /**
     * 所有字段更新
     * @param entity
     * @return
     */
    public int updateAll(T entity){
        setUpdateTime(entity);
        return sqlManager.updateAll(getTClass(),entity);
    }

    /**
     * 条件更新
     * @param entity
     * @return
     */
    public int updateTplById(T entity) {
        setUpdateTime(entity);
        return sqlManager.updateTemplateById(entity);
    }

    /**
     * 批量更新
     * @param entities
     * @return
     */
    public int[] updateBatch(List<T> entities){
        return sqlManager.updateByIdBatch(entities);
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    public int logicDel(Object id) {
        T entity = one(id);
        setDeleteTime(entity);
        return update(entity);
    }

    /**
     * 真实删除
     *
     * @param id
     * @return
     */
    public int del(Object id) {
        return sqlManager.deleteById(getTClass(), id);
    }

    /**
     * 根据数据对象删除
     * @param object
     * @return
     */
    public int delByObject(Object object){
        return sqlManager.deleteObject(object);
    }

    /**
     * 锁表
     * @param id
     * @return
     */
    public T lock(Object id) {
        return (T) sqlManager.lock(getTClass(), id);
    }

    private void setCreateTime(T entity) {
        try {
            Method method = entity.getClass().getMethod("setCrAt", Date.class);
            method.invoke(entity, new Date());
        } catch (NoSuchMethodException e) {

        } catch (IllegalAccessException e) {
            log.warn("调用方法错误");
        } catch (InvocationTargetException e) {
            log.warn("调用方法错误");
        }
    }

    private void setUpdateTime(T entity) {
        try {
            Method method = entity.getClass().getMethod("setUpAt", Date.class);
            method.invoke(entity, new Date());
        } catch (NoSuchMethodException e) {

        } catch (IllegalAccessException e) {
            log.warn("调用方法错误");
        } catch (InvocationTargetException e) {
            log.warn("调用方法错误");
        }
    }

    private void setDeleteTime(T entity) {
        try {
            Method method = entity.getClass().getMethod("setDeAt", Date.class);
            method.invoke(entity, new Date());
        } catch (NoSuchMethodException e) {

        } catch (IllegalAccessException e) {
            log.warn("调用方法错误");
        } catch (InvocationTargetException e) {
            log.warn("调用方法错误");
        }
    }


}
