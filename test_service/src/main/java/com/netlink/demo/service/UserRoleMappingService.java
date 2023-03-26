package com.netlink.demo.service;

import com.netlink.demo.dto.SystemUserDTO;
import com.netlink.demo.dto.SystemUserRoleDTO;
import com.netlink.demo.dto.UserRoleMappingDTO;
import com.netlink.demo.model.SystemUser;
import com.netlink.demo.model.UserRole;
import com.netlink.demo.model.UserRoleMapping;
import com.netlink.demo.repository.UserRoleMappingRepository;
import com.netlink.demo.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserRoleMappingService implements IUserRoleMappingService {

    @Autowired
    private UserRoleMappingRepository userRoleMappingRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;


    @Override
    public List<UserRoleMapping> findAllUserRole() {
        List<UserRoleMapping> roleMappings = userRoleMappingRepository.findAll();

        Collections.sort(roleMappings, new Comparator<UserRoleMapping>() {
            @Override
            public int compare(UserRoleMapping o1, UserRoleMapping o2) {
                if (o1.getCreatedDate() == null && o2.getCreatedDate() == null) {
                    return 0;
                }
                return o1.getCreatedDate().compareTo(o2.getCreatedDate());
            }
        });
        return roleMappings;
    }

    @Override
    public void saveUserRoles(SystemUserDTO userDTO) {
        List<UserRoleMapping> list = new ArrayList<>();
        transformDtoToList(userDTO, list);
        for (UserRoleMapping urmToBePersisted : list) {
            userRoleMappingRepository.save(urmToBePersisted);
        }
    }


    private void transformDtoToList(SystemUserDTO userDTO, List<UserRoleMapping> list) {
        UserRole userRole = null;
        SystemUser systemUser = null;
        UserRoleMapping userRoleMapping = null;
        for (SystemUserRoleDTO ur : userDTO.getAssignedRoles()) {
            userRole = new UserRole();
            userRoleMapping = new UserRoleMapping();
            userRole.setId(ur.getRoleId());
            systemUser = new SystemUser();
            systemUser.setId(userDTO.getId());
            userRoleMapping.setSysUserId(systemUser);
            userRoleMapping.setRoleId(userRole);
            list.add(userRoleMapping);
        }
    }

    @Override
    public List<UserRoleMapping> findBySysUserId_Id(Long id) {
        List<UserRoleMapping> userRoleMappingList = userRoleMappingRepository.findBySysUserId_Id(id);
        return userRoleMappingList;
    }

    @Override
    public void removeUserRoles(SystemUserDTO userDTO) {
        Long roleId = null;
        Long systemUserId = null;
        List<UserRoleMapping> list = new ArrayList<>();
        transformDtoToList(userDTO, list);
        for (UserRoleMapping roleToBeRemoved : list) {
            roleId = roleToBeRemoved.getRoleId().getId();
            systemUserId = roleToBeRemoved.getSysUserId().getId();
            deleteByRoleIdAndSysUserId(roleToBeRemoved.getRoleId(), roleToBeRemoved.getSysUserId());
        }
    }

    @Override
    public Integer deleteByRoleIdAndSysUserId(UserRole userRole, SystemUser sysUser) {
        return userRoleMappingRepository.deleteByRoleIdAndSysUserId(userRole, sysUser);
    }

    public void existingUserRoles(SystemUserDTO systemUserDTO) {
        SystemUserRoleDTO systemUserRoleDTO = null;
        List<SystemUserRoleDTO> surList = new ArrayList<>();
        List<SystemUserRoleDTO> userRoleDTOList = new ArrayList<>();
        List<UserRoleMapping> userRoleMappingList = userRoleMappingRepository.findBySysUserId_Id(systemUserDTO.getId());
        if (userRoleMappingList != null && !userRoleMappingList.isEmpty()) {
            for (UserRoleMapping urm : userRoleMappingList) {
                systemUserRoleDTO = new SystemUserRoleDTO();
                systemUserRoleDTO.setRoleId(urm.getRoleId().getId());
                systemUserRoleDTO.setRoleDescription(urm.getRoleId().getDescription());
                systemUserRoleDTO.setRoleName(urm.getRoleId().getRoleName());
                surList.add(systemUserRoleDTO);
            }
            if (systemUserDTO.getAssignedRoles().size() == 0 || systemUserDTO.getAssignedRoles().size() < surList.size()) {
                userRoleDTOList = collectDistinctElem(surList, systemUserDTO.getAssignedRoles());
                systemUserDTO.setIsRoleUnAssigned(Boolean.TRUE);
            } else {
                userRoleDTOList = collectDistinctElem(systemUserDTO.getAssignedRoles(), surList);
                systemUserDTO.setIsRoleUnAssigned(Boolean.FALSE);
            }
            systemUserDTO.setAssignedRoles(userRoleDTOList);
        }
    }


    private List<SystemUserRoleDTO> collectDistinctElem(List<SystemUserRoleDTO> list1, List<SystemUserRoleDTO> list2) {
        return list1.stream().filter(_o1 -> list2.stream().noneMatch(_o2 -> _o2.getRoleId().equals(_o1.getRoleId())))
                .collect(Collectors.toList());
    }


    @Override
    public boolean saveUserRole(UserRoleMappingDTO userRoleMappingDTO) throws Exception {
        UserRoleMapping urm;
        List<UserRole> roleList = userRoleMappingDTO.getRoleId();
        if (roleList.isEmpty() == false) {
            for (UserRole role : roleList) {
                urm = new UserRoleMapping();
                urm.setRoleId(role);
                urm.setSysUserId(userRoleMappingDTO.getSysUserId());
                userRoleMappingRepository.save(urm);
            }
        }
        return true;
    }

}




