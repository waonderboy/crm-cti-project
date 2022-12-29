package com.brogs.crm.common;

import com.brogs.crm.domain.agentaccount.AccountInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class AccountPrincipal implements UserDetails {
    private String identifier;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    public static AccountPrincipal from(AccountInfo.Main info) {
        return new AccountPrincipal(info.getIdentifier(),
                info.getPassword(),
                info.getAuthorities()
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet()));
    }


    @Override
    public String getUsername() {
        return identifier;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
