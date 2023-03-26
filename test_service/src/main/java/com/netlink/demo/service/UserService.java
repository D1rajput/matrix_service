package com.netlink.demo.service;


import com.netlink.demo.constants.ErrorMessage;
import com.netlink.demo.dto.SystemUserDTO;
import com.netlink.demo.model.SystemUser;
import com.netlink.demo.repository.SystemUserRepository;
import com.netlink.demo.utility.JSONWebTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    @Autowired
    JSONWebTokenGenerator tokenGenerator;
    @Autowired
    SystemUserRepository systemUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (null == username)
            throw new BadCredentialsException(this.messages.getMessage("EmptyUsername", "Empty username not allowed!"));
        SystemUser systemUser = null;
        if (username.contains("@")) {
            systemUser = systemUserRepository.findByEmail(username);
            if (systemUser.getActive() == true) {
                UserDetails userDetails = new User(systemUser.getEmail(), systemUser.getPassword(), new ArrayList<>());
                return userDetails;
            } else {
                //throw new UsernameNotFoundException(ErrorMessage.USER_DEACTIVATE);
                throw new BadCredentialsException(this.messages.getMessage("UserDeactivated", "User access revoked, please contact admin"));
            }
        } else {
            systemUser = systemUserRepository.findByContact(username);
            if (systemUser.getActive() == true) {
                UserDetails userDetails = new User(systemUser.getContact(), systemUser.getPassword(), new ArrayList<>());
                return userDetails;
            } else {
                //throw new UsernameNotFoundException(ErrorMessage.USER_DEACTIVATE);
                throw new BadCredentialsException(this.messages.getMessage("UserDeactivated", "User access revoked, please contact admin"));
            }
        }


    }

    public String issueAuthToken(String authenticatedUser) throws Exception {
        if (null == authenticatedUser || authenticatedUser.isEmpty()) {
            throw new Exception(ErrorMessage.USER_IS_NOT_VALIDATE);
        }
        return doGenerateAuthToken(authenticatedUser);
    }

    private final String doGenerateAuthToken(final String authenticatedUser) throws Exception {
        String token = null;
        final UserDetails userDetails = loadUserByUsername(authenticatedUser);
        if (null != userDetails) {
            token = tokenGenerator.generateToken(userDetails);
        } else {
            throw new UsernameNotFoundException(ErrorMessage.CUSTOMER_IS_NOT_FOUND);
        }
        return token;
    }

    public SystemUserDTO getUser(String email) {
        SystemUser user = null;
        if (email.contains("@")) {
            user = systemUserRepository.findByEmail(email);
        } else {
            user = systemUserRepository.findByContact(email);
        }
        SystemUserDTO output = new SystemUserDTO();
        output.setId(user.getId());
        output.setName(user.getFirstName());
        output.setEmail(user.getEmail());
        output.setPassword(user.getPassword());
        return output;
    }
}
