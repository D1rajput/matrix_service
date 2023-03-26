package com.netlink.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserRoleDTO {
    private Long roleId;
    private String roleName;
    private String roleDescription;
    private List<SystemUserDTO> systemUserDTOList;
}
