package com.wakabatimes.simplewiki.app.domain.model.user;

import java.util.UUID;

public class UserFactory {
    public static User create(UserName userName, UserPassword userPassword){
        UserId userId = new UserId(UUID.randomUUID().toString());
        UserRole userRole = UserRole.ROLE_USER;
        return new User(userId, userName, userPassword, userRole);
    }
}
