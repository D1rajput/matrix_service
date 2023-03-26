package com.netlink.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleManagementDTO {
    private Long id;
    private String name;
    private String description;
    private Boolean active;

}