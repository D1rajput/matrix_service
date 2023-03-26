package com.netlink.demo.service;

import com.netlink.demo.constants.ErrorMessage;
import com.netlink.demo.dto.RoleDTO;
import com.netlink.demo.dto.SystemUserDTO;
import com.netlink.demo.model.SystemUser;
import com.netlink.demo.model.UserRole;
import com.netlink.demo.model.UserRoleMapping;
import com.netlink.demo.repository.SystemUserRepository;
import com.netlink.demo.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserRoleService implements IUserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;


    @Autowired
    private SystemUserRepository systemUserRepository;

    @Override
    public List<UserRole> findAllRoles() throws Exception {
        List<UserRole> userRoles = userRoleRepository.findAll();
        if (userRoles != null) {
            return userRoles;
        }
        throw new Exception(ErrorMessage.No_Data_Found);
    }

    @Override
    public List<RoleDTO> findAll() throws Exception {
        List<RoleDTO> userList = new ArrayList<>();
        SystemUserDTO systemUserDTO;
        RoleDTO roleDTO;
        SystemUser systemUser;
        List<UserRole> role = userRoleRepository.findAll();
        if (role != null) {
            for (UserRole su : role) {
                roleDTO = new RoleDTO();
                roleDTO.setId(su.getId());
                roleDTO.setName(su.getRoleName());
                roleDTO.setDescription(su.getDescription());
                roleDTO.setActive(su.getIsActive());

                if (su.getUpdatedBy() != null) {
                    systemUser = systemUserRepository.findById(su.getUpdatedBy()).orElse(null);
                    ;
                    roleDTO.setUpdatedBy(systemUser.getFirstName().concat(" " + systemUser.getLastName()));
                }
                roleDTO.setUpdatedDate(su.getUpdatedDate().toLocalDate());
                List<SystemUserDTO> assignedUsers = new ArrayList<>();
                for (UserRoleMapping ur : su.getUserRoleMappings()) {
                    systemUserDTO = new SystemUserDTO();
                    systemUserDTO.setId(ur.getSysUserId().getId());
                    systemUserDTO.setFirstName(ur.getSysUserId().getFirstName());
                    systemUserDTO.setLastName(ur.getSysUserId().getLastName());
                    systemUserDTO.setName(ur.getSysUserId().getFirstName().concat(" " + ur.getSysUserId().getLastName()));
                    systemUserDTO.setEmail(ur.getSysUserId().getEmail());
                    assignedUsers.add(systemUserDTO);
                }
                roleDTO.setSystemUserDTOS(assignedUsers);
                userList.add(roleDTO);
            }
            return userList;
        }
        throw new Exception(ErrorMessage.No_Data_Found);
    }

    @Override
    public Boolean saveUserRole(RoleDTO roleDTO) throws Exception {
        UserRole role = new UserRole();
        role.setId(roleDTO.getId());
        role.setRoleName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());
        role.setIsActive(roleDTO.getActive());
        role.setCreatedBy(roleDTO.getCreatedBy());
        role.setUpdatedBy(roleDTO.getCreatedBy());
        role.setUpdatedDate(LocalDateTime.now());
        role.setCreatedDate(LocalDateTime.now());
        UserRole data = userRoleRepository.save(role);
        if (data != null) {
            return true;
        }
        throw new Exception("Role is not save");
    }

}


