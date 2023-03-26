package com.netlink.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "user_role")
public class UserRole extends BaseEntity implements Serializable {

    @Column(name = "name")
    private String roleName;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive;


    @OneToMany(mappedBy = "roleId", cascade = CascadeType.ALL)
    @JsonBackReference(value = "UserRole")
    private List<UserRoleMapping> userRoleMappings;

}
