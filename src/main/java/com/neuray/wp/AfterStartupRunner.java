////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp;
import cn.hutool.core.io.FileUtil;
import com.neuray.wp.service.RedisCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${pic.root.path}")
    private String picRootPath;
    @Autowired
    private RedisCacheService redisCacheService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        redisCacheService.refreshDictCache();
        redisCacheService.refreshSysConfCache();
        if (FileUtil.exist(picRootPath)) {
            FileUtil.mkdir(picRootPath);
        }
        log.info("::::::::::::::::系统启动成功::::::::::::::::");
    }


}
