package com.netlink.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class SystemUserDTO {
    private Long id;
    private String name;
    private String FirstName;
    private String lastName;
    private String contact;
    private String email;
    private Long createdBy;
    private Long updatedBy;
    private String password;
    private Boolean isRoleUnAssigned;
    private List<SystemUserRoleDTO> assignedRoles;
}
