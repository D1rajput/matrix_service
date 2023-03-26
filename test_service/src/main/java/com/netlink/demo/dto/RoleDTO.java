package com.netlink.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Long id;
    private String name;
    private String description;
    private Boolean active;
    private LocalDateTime createdDate;
    private Long createdBy;
    private String updatedBy;
    private LocalDate updatedDate;
    private List<SystemUserDTO> systemUserDTOS;
    private List<SystemUserDTO> deselectSystemUser;
}
