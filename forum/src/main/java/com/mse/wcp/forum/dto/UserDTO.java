package com.mse.wcp.forum.dto;

import com.mse.wcp.forum.persistence.entity.Role;

import lombok.Data;

@Data
public class UserDTO {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;

}
