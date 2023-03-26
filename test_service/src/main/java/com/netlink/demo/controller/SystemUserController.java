package com.netlink.demo.controller;

import com.netlink.demo.model.SystemUser;
import com.netlink.demo.model.TokenResponse;
import com.netlink.demo.repository.SystemUserRepository;
import com.netlink.demo.service.UserService;
import com.netlink.demo.utility.JSONWebTokenGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SystemUserController {

    private static final Logger logger = LogManager.getLogger(SystemUserController.class);


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JSONWebTokenGenerator tokenGenerator;

    @Autowired
    SystemUserRepository userRepo;

    @Autowired
    UserService service;

    @GetMapping("/test")
    public String getMsg() {
        return "Hello";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody SystemUser management) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = null;
        TokenResponse tokenResponse = new TokenResponse();
        try {

            authenticationToken = new UsernamePasswordAuthenticationToken(management.getEmail(), management.getPassword());
            authenticationManager.authenticate(authenticationToken);
            final UserDetails userDetails = service.loadUserByUsername(management.getEmail());
            final String token = tokenGenerator.generateToken(userDetails);

            tokenResponse.setToken(token);
            tokenResponse.setSystemUser(service.getUser(management.getEmail()));

            logger.info("Authentication Successfull");
            return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage() + "Authentication failed");
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

}
