package com.netlink.demo.repository;


import com.netlink.demo.model.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {
    SystemUser findByEmail(String email);

    @Query(value = "select * from system_user where contact = ?1 ", nativeQuery = true)
    SystemUser findByContact(String number);

}