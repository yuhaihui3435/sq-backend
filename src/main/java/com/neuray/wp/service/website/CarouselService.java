////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2019. 东睿科技有限公司.保留所有权利
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.service.website;

import com.neuray.wp.core.BaseService;
import com.neuray.wp.entity.website.Carousel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author:小听风 创建时间:2019/6/29
 * 版本:v1.0
 * @Description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CarouselService extends BaseService<Carousel> {
}
