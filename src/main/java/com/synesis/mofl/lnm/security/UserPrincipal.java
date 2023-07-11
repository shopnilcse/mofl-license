package com.synesis.mofl.lnm.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.synesis.mofl.lnm.model.User;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"rawtypes", "unchecked"})
@Getter @Setter
public class UserPrincipal implements UserDetails {

    private static final long serialVersionUID = -695944358349132044L;

    private Long id;

    private String name;

    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String name, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
    public Long getId() {
        return this.id;
    }

    public static UserPrincipal create(User user){
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_" + "ADMIN");

        return new UserPrincipal(user.getId(), user.getName(), user.getUsername(),user.getEmail(), user.getPassword(), grantedAuthorities );

    }

    public static Set<GrantedAuthority> convertToGrantedAuthority(ArrayList authorityList) {
        Set<GrantedAuthority> authorities = new HashSet();
        if (!ObjectUtils.isEmpty(authorityList)) {
            for (Object entry : authorityList) {
                Map<String,String> value = (Map<String,String>) entry;
                authorities.add(new SimpleGrantedAuthority(value.get("authority")));
            }
        }
        return  authorities;
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
