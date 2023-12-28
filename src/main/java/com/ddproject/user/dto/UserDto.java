package com.ddproject.user.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class UserDto extends User {

    private String username;
    private String password;
    public UserDto(String username, String password, Collection<GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.username = username;
        this.password = password;
    }
}
