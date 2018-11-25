package com.wakabatimes.simplewiki.app.domain.model.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;
import java.util.ArrayList;

/**
 * root entity
 */
@Slf4j
@EqualsAndHashCode
public class User extends org.springframework.security.core.userdetails.User{
    @Getter
    @NonNull
    UserId userId;

    @Getter
    UserName userName;

    @Getter
    @NonNull
    UserPassword userPassword;

    public User(UserId userId, UserName userName, UserPassword userPassword) {
        super(userName.getValue(), userPassword.getValue(), new ArrayList<GrantedAuthority>());
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public static User fromPrincipal(Principal principal) {
        Authentication auth = (Authentication) principal;
        return (User) auth.getPrincipal();
    }
}
