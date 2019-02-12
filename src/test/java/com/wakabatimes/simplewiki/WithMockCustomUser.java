package com.wakabatimes.simplewiki;

import com.wakabatimes.simplewiki.app.domain.model.user.*;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    UserId userId = new UserId("443ee7b9-76f5-43bb-a2c1-225d0fecfea6");
    UserName userName = new UserName("testUser");
    UserPassword userPassword = new UserPassword("$2a$10$OARCpRLqglO6nZzRHVVj1e5pbxPNKpSnXfmT11Sx5i4Nzt4Sj7YUi");
    UserRole userRole = UserRole.ROLE_ADMIN;
    User user = new User(userId,userName,userPassword,userRole); 
}
