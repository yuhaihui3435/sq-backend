package com.neuray.wp.model;

import com.neuray.wp.entity.RoleUser;
import lombok.Data;

@Data
public class UserRoleDto {
    private Long sysUserId;
    private RoleUser[] roleUsers;
}
