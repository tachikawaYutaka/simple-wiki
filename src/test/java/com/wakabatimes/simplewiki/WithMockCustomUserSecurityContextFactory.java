package com.wakabatimes.simplewiki;

import com.wakabatimes.simplewiki.app.domain.model.user.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.ArrayList;
import java.util.List;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        List<String> authorities = new ArrayList<>();
        switch(customUser.userRole.getId()){
            case(0) :
                authorities.add(UserRole.ROLE_ADMIN.name());
                authorities.add(UserRole.ROLE_EDITOR.name());
                authorities.add(UserRole.ROLE_USER.name());
                break;
            case(1) :
                authorities.add(UserRole.ROLE_EDITOR.name());
                authorities.add(UserRole.ROLE_USER.name());
                break;
            case(2) :
                authorities.add(UserRole.ROLE_USER.name());
                break;
        }

        CustomUserDetails principal = new CustomUserDetails(customUser.userId,customUser.userName,
                customUser.userPassword, customUser.userRole);
        Authentication auth = new UsernamePasswordAuthenticationToken(principal,
                "password", principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }

    public class CustomUserDetails extends User {
        public CustomUserDetails(UserId userId, UserName userName,UserPassword userPassword,UserRole userRole) {
            super(userId, userName, userPassword, userRole);
        }
    }
}
