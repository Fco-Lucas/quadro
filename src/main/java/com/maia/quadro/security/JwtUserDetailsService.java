package com.maia.quadro.security;

import com.maia.quadro.enums.UserRole;
import com.maia.quadro.model.AppUser;
import com.maia.quadro.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        AppUser appUser = userService.getUserByCpf(cpf);
        return new JwtUserDetails(appUser);
    }

    public JwtToken getTokenAuthenticated(String cpf) {
        UserRole role = userService.getRoleByCpf(cpf);
        return JwtUtils.createToken(cpf, role.name());
    }
}
