package com.neuray.wp.model;

import com.neuray.wp.entity.UserDept;
import lombok.Data;

@Data
public class UserDeptDto {

    private UserDept[] userDepts;

    private Long userId;


}
