package com.netlink.demo.controller;

import com.netlink.demo.dto.RoleDTO;
import com.netlink.demo.dto.UserRoleMappingDTO;
import com.netlink.demo.service.UserRoleMappingService;
import com.netlink.demo.service.UserRoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RoleManagementController {
    private static final Logger logger = LogManager.getLogger(RoleManagementController.class);

    @Autowired
    UserRoleService iUserRoleService;

    @Autowired
    UserRoleMappingService iUserRoleMappingService;

    @GetMapping("/getData")
    public ResponseEntity<List<RoleDTO>> findAllRoleData() {
        try {
            logger.info("Fetching all role data ");
            List<RoleDTO> roleDTO = iUserRoleService.findAll();
            return new ResponseEntity<>(roleDTO, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Boolean> saveRole(@RequestBody RoleDTO roleDTO) throws Exception {
        try {
            logger.info("Saving Role");
            Boolean response = iUserRoleService.saveUserRole(roleDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/saveRole")
    public ResponseEntity<Boolean> saveRole(@RequestBody UserRoleMappingDTO userRoleMapping) throws Exception {
        logger.info("save role " + userRoleMapping.getRoleId());
        Boolean response = iUserRoleMappingService.saveUserRole(userRoleMapping);
        try {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }


}
