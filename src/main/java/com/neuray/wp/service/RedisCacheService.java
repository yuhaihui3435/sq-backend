package com.neuray.wp.service;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neuray.wp.entity.Dict;
import com.neuray.wp.entity.DictItem;
import com.neuray.wp.entity.SysConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisCacheService implements Serializable{
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private DictService dictService;
    @Autowired
    private SysConfService sysConfService;
    public static final String DICT_CACHE_NAME="dict_cache_";
    public static final String DICT_CACHE_JSON_NAME="dict_cache_json";
    public static final String DICT_ITEM_CACHE_NAME="dict_item_cache_";
    public static final String DICT_ITEM_CACHE_JSON_NAME="dict_item_cache_json";
    public static final String SYS_CONF_CACHE_NAME="sys_conf_cache_";
    public static final String SYS_CONF_CACHE_JSON_NAME="sys_conf_cache_json";

    /**
     *
     * 刷新数据字典缓存
     *
     */
    public void refreshDictCache(){
        List<Dict> dictList=dictService.many("dict.sample", Dict.builder().build());
        dictList.stream().forEach(dict -> {
            List<DictItem> dictItemList=dictItemService.many("dictItem.sample", DictItem.builder().dictId(dict.getId()).build());
            redisTemplate.opsForHash().put(DICT_CACHE_NAME,DICT_CACHE_NAME+dict.getDictVal(),dictItemList);
            redisTemplate.opsForHash().put(DICT_CACHE_JSON_NAME,DICT_CACHE_JSON_NAME+dict.getDictVal(), JSONUtil.toJsonStr(dictItemList));
            dictItemList.stream().forEach(dictItem -> {
                redisTemplate.opsForHash().put(DICT_ITEM_CACHE_NAME,DICT_ITEM_CACHE_NAME+dictItem.getId(),dictItem);
                redisTemplate.opsForHash().put(DICT_ITEM_CACHE_JSON_NAME,DICT_ITEM_CACHE_JSON_NAME+dictItem.getId(),JSONUtil.toJsonStr(dictItem));
            });
        });
        log.info("数据字典缓存加载成功");
    }

    /**
     * 根据字典val获取条目集合
     * @param val
     * @return
     */

    public List<DictItem> getDictItemByDictVal(String val){
        return (List<DictItem>)redisTemplate.opsForHash().get(DICT_CACHE_NAME,DICT_CACHE_NAME+val);
    }

    /**
     * 根据字典条目id获取条目数据
     * @param id
     * @return
     */

    public DictItem getDictItemById(Long id){
        return (DictItem)redisTemplate.opsForHash().get(DICT_ITEM_CACHE_NAME,DICT_ITEM_CACHE_NAME+id);
    }


    /**
     * 加载系统配置参数
     */
    public void refreshSysConfCache(){
        List<SysConf> sysConfs=sysConfService.all();
        SysConf sysConf=null;
        for (int i = 0; i < sysConfs.size(); i++) {
            sysConf=sysConfs.get(i);
            redisTemplate.opsForHash().put(SYS_CONF_CACHE_NAME,SYS_CONF_CACHE_NAME+sysConf.getScKey(),sysConf);
            redisTemplate.opsForHash().put(SYS_CONF_CACHE_JSON_NAME,SYS_CONF_CACHE_JSON_NAME+sysConf.getScKey(),JSONUtil.toJsonStr(sysConf));
        }
        redisTemplate.opsForValue().set(SYS_CONF_CACHE_NAME+"_all",sysConfs);
        redisTemplate.opsForValue().set(SYS_CONF_CACHE_JSON_NAME+"_all",JSONUtil.toJsonStr(sysConfs));
        log.info("系统配置参数加载成功");
    }

    public SysConf getSysConf(String scKey ){
        return (SysConf)redisTemplate.opsForHash().get(SYS_CONF_CACHE_NAME,SYS_CONF_CACHE_NAME+scKey);
    }

    public List<SysConf> getAllSysConf(){
        return (List<SysConf>)redisTemplate.opsForValue().get(SYS_CONF_CACHE_NAME+"_all");
    }

    public Object findVal(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void addVal(String key,Object val){
        redisTemplate.opsForValue().set(key,val);
    }

    public void delVal(String key){
        redisTemplate.delete(key);
    }

    public void expired(String key, long timeout, TimeUnit timeUnit){
        redisTemplate.expire(key,timeout,timeUnit);
    }

    public Object findHVal(String hKey,String key){
        return redisTemplate.opsForHash().get(hKey,key);
    }

    public void addHVal(String hKey,String key,Object val){
        redisTemplate.opsForHash().put(hKey,key,val);
    }

    public void delHval(String hkey,Object... objects){
        redisTemplate.opsForHash().delete(hkey,objects);
    }


    public boolean exist(String key){
        return redisTemplate.hasKey(key);
    }

    public boolean existH(String hkey,String key){
        return redisTemplate.opsForHash().hasKey(hkey,key);
    }

}
