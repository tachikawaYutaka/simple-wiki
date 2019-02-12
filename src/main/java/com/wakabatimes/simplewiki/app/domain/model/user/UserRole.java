package com.wakabatimes.simplewiki.app.domain.model.user;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum UserRole {
    ROLE_ADMIN(0),
    ROLE_EDITOR(1),
    ROLE_USER(2);

    @Getter
    private Integer id;

    private UserRole(Integer id) {
        this.id = id;
    }

    public static UserRole getById(Integer id) {
        for(UserRole userRole : UserRole.values()){
            if(userRole.id == id){
                return userRole;
            }
        }
        return UserRole.ROLE_USER;
    }

}