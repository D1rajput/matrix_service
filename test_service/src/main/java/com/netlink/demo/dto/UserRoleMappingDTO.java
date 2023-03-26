package com.netlink.demo.dto;

import com.netlink.demo.model.SystemUser;
import com.netlink.demo.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleMappingDTO {
    private SystemUser sysUserId;
    private List<UserRole> roleId;
    private List<SystemUserDTO> deselectSystemUser;
}

