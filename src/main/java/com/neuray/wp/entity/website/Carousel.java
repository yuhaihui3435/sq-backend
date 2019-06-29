////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2019. 东睿科技有限公司.保留所有权利
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.entity.website;

import com.neuray.wp.core.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

/**
 * @author:小听风 创建时间:2019/6/29
 * 版本:v1.0
 * @Description:
 */
@Table(name = "CAROUSEL_T")
@Data
@Builder
public class Carousel extends BaseEntity {
    @Tolerate
    public Carousel() {
    }

    @AssignID
    private Long id;

    private String title;//标题

    private String subTitle;//副标题

    private Integer order;//顺序

    private String url;//链接

    private String status;//状态

    private Date expiredAt;//过期时间

    private Date effectiveAt;//有效期时间

    private String img;//图片路径

    //创建时间
    private Date crAt;

    //删除人
    private Long deBy;

    //更新时间
    private Date upAt;

    //创建人
    private Long crBy;

    //删除时间
    private Date deAt;

    //更新人
    private Long upBy;

}
