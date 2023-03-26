package com.netlink.demo.repository;

import com.netlink.demo.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query(value = "select * from user_role ae where ae.name=?1;", nativeQuery = true)
    UserRole findByRoleName(String role);
}
