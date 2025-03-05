package com.maia.quadro.security;

import com.maia.quadro.model.AppUser;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.UUID;

public class JwtUserDetails extends User {
    private AppUser appUser;

    public JwtUserDetails(AppUser appUser) {
        super(appUser.getCpf(), appUser.getPassword(), AuthorityUtils.createAuthorityList(appUser.getRole().name()));
        this.appUser = appUser;
    }

    public UUID getId() {
        return this.appUser.getId();
    }

    public String getRole() {
        return this.appUser.getRole().name();
    }
}
