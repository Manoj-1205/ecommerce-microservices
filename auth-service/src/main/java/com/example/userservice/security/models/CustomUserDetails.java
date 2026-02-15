package com.example.userservice.security.models;

import com.example.userservice.exceptions.NoRolesFoundException;
import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.models.Role;
import com.example.userservice.models.User;
import com.example.userservice.security.services.CustomUserDetailService;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonDeserialize
@NoArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {


    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    private Long userId;

    private List<CustomGrantedAuthority> authorities;
    public CustomUserDetails(User user){
        authorities=new ArrayList<>();
        for(Role role : user.getRoles()){
            authorities.add(new CustomGrantedAuthority(role));
        }

        username = user.getEmail();
        password=user.getPassword();
        accountNonExpired=true;
        accountNonLocked=true;
        credentialsNonExpired=true;
        enabled=true;
        this.userId=user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}
