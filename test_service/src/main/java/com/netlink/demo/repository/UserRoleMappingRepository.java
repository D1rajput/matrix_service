package com.netlink.demo.repository;

import com.netlink.demo.model.SystemUser;
import com.netlink.demo.model.UserRole;
import com.netlink.demo.model.UserRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRoleMappingRepository extends JpaRepository<UserRoleMapping, Long>, JpaSpecificationExecutor<UserRoleMapping> {

    public UserRoleMapping save(UserRoleMapping userRoleMapping);

    public List<UserRoleMapping> findAllByRoleId(UserRole id);

    public List<UserRoleMapping> findBySysUserId_Id(Long id);

    public Integer deleteByRoleIdAndSysUserId(UserRole roleId, SystemUser sysUserId);

}
