package com.netlink.demo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user_role_mapping")
public class UserRoleMapping extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "sys_user_id")
    private SystemUser sysUserId;


    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole roleId;

}
