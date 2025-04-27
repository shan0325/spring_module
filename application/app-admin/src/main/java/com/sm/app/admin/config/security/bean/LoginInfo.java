package com.sm.app.admin.config.security.bean;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@ToString
public class LoginInfo extends User {
    private String memberId;
    private String authority;
    private String name;

    public LoginInfo(String username, String password, Collection<? extends GrantedAuthority> authorities,
                     String authority, String name) {
        super(username, password, authorities);

        this.memberId = username;
        this.authority = authority;
        this.name = name;
    }
}
