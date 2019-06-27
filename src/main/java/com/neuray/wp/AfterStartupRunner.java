////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp;
import com.neuray.wp.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 系统启动后回调处理
 * 时间 2018/12/26
 * @author 小听风
 * @version v1.0
 * @see
 * @since
 */
@Component
@Slf4j
public class AfterStartupRunner implements ApplicationRunner
{


    @Autowired
    private CacheService cacheService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        cacheService.refreshDictCache();
        cacheService.refreshSysConfCache();
        log.info("::::::::::::::::系统启动成功::::::::::::::::");
    }


}
