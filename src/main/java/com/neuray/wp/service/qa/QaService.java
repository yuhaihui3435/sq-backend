////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////
package com.neuray.wp.service.qa;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.qa.Qa;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import cn.hutool.core.util.StrUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class QaService extends BaseService<Qa> {

}