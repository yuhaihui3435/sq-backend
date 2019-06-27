package com.neuray.wp.controller;

import com.neuray.wp.Consts;
import com.neuray.wp.annotation.LogDog;
import com.neuray.wp.core.BaseController;
import com.neuray.wp.entity.Dict;
import com.neuray.wp.entity.DictItem;
import com.neuray.wp.entity.SysConf;
import com.neuray.wp.service.CacheService;
import com.neuray.wp.service.SysConfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用controller
 * 时间 2019/2/1
 *
 * @author 小听风
 * @version v1.0
 * @see
 * @since
 */
@Slf4j
@RestController
@RequestMapping("/cc")
public class CommonController extends BaseController {

    @Autowired
    private CacheService cacheService;

    /**
     * 根据字典值获取字典条目集合
     *
     * @param dict
     * @return
     */
//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/getDictItemByDictVal")
    public List<DictItem> getDictItemByDictVal(@RequestBody Dict dict) {
        return cacheService.getDictItemByDictVal(dict.getDictVal());
    }

//    @LogDog(logType = Consts.LOGTYPE.QUERY, reqSource = Consts.REQSOURCE.INNER)
    @PostMapping("/getAllSysConf")
    public Map<String, String> getAllSysConf() {
        Map<String, String> map = new HashMap<>();
        List<SysConf> sysConfs=cacheService.getAllSysConf();
        if (sysConfs != null) {
            sysConfs.stream().forEach(sysConf -> {
                map.put(sysConf.getScKey(), sysConf.getScVal());
            });
        }

        return map;
    }

}
