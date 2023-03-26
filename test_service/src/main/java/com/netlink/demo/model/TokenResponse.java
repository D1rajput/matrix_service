package com.netlink.demo.model;

import com.netlink.demo.dto.SystemUserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse implements Serializable {

    private String token;
    private SystemUserDTO systemUser;
}
