package com.neuray.wp.model;

import com.neuray.wp.entity.DeptRole;
import lombok.Data;

@Data
public class DeptRoleDto {
    private DeptRole[] deptRoles;
    private Long deptId;
}
