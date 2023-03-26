package com.netlink.demo.service;

import com.netlink.demo.dto.RoleDTO;
import com.netlink.demo.model.UserRole;

import java.util.List;

public interface IUserRoleService {
    public List<UserRole> findAllRoles() throws Exception;

    public List<RoleDTO> findAll() throws Exception;

    Boolean saveUserRole(RoleDTO roleDTO) throws Exception;
}
