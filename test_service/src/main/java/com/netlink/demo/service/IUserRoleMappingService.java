package com.netlink.demo.service;

import com.netlink.demo.dto.SystemUserDTO;
import com.netlink.demo.dto.UserRoleMappingDTO;
import com.netlink.demo.model.SystemUser;
import com.netlink.demo.model.UserRole;
import com.netlink.demo.model.UserRoleMapping;

import java.util.List;

public interface IUserRoleMappingService {
    public void saveUserRoles(SystemUserDTO userDTO);

    public List<UserRoleMapping> findBySysUserId_Id(Long id);

    public void removeUserRoles(SystemUserDTO userDTO);

    public Integer deleteByRoleIdAndSysUserId(UserRole roleId, SystemUser sysUserId);

    public List<UserRoleMapping> findAllUserRole();

    boolean saveUserRole(UserRoleMappingDTO userRoleMappingDTO) throws Exception;

}
