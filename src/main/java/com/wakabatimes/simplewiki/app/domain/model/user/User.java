package com.wakabatimes.simplewiki.app.domain.model.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * root entity
 */
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class User extends org.springframework.security.core.userdetails.User{
    @Getter
    @NonNull
    UserId userId;

    @Getter
    UserName userName;

    @Getter
    @NonNull
    UserPassword userPassword;

    @Getter
    @NonNull
    UserRole userRole;

    public User(UserId userId, UserName userName, UserPassword userPassword, UserRole userRole) {
        super(userName.getValue(), userPassword.getValue(), new ArrayList<GrantedAuthority>());
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        List<String> roles = new ArrayList<>();
        switch(userRole.getId()){
            case(0) :
                roles.add(UserRole.ROLE_ADMIN.name());
                roles.add(UserRole.ROLE_EDITOR.name());
                roles.add(UserRole.ROLE_USER.name());
                break;
            case(1) :
                roles.add(UserRole.ROLE_EDITOR.name());
                roles.add(UserRole.ROLE_USER.name());
                break;
            case(2) :
                roles.add(UserRole.ROLE_USER.name());
                break;
        }
        return AuthorityUtils.createAuthorityList(roles.toArray(new String[]{}));
    }
}
