package com.wakabatimes.simplewiki.app.domain.model.user;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;
import java.util.Collection;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

public class UserTest {
    @Test
    public void createInsntace_success() {
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        UserRole userRole = UserRole.ROLE_USER;
        User user = UserFactory.create(userName, userPassword, userRole);

        Assert.assertNotNull(user);
    }

    @Test
    public void equals() {
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        UserRole userRole = UserRole.ROLE_USER;
        User user = UserFactory.create(userName, userPassword, userRole);

        UserName userName2 = new UserName("hogehoge");
        UserPassword userPassword2 = new UserPassword("hogehoge");
        UserRole userRole2 = UserRole.ROLE_USER;
        User user2 = UserFactory.create(userName2, userPassword2, userRole2);

        Assert.assertFalse(user.equals(user2));
    }

    @Test
    public void getUserName() {
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        UserRole userRole = UserRole.ROLE_USER;
        User user = UserFactory.create(userName, userPassword, userRole);

        Assert.assertEquals("hogehoge", user.getUserName().getValue());
    }

    @Test
    public void getAdminAuthorities(){
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        UserRole userRole = UserRole.ROLE_USER;
        User user = UserFactory.create(userName, userPassword, userRole);
        Collection<GrantedAuthority> grantedAuthorities = user.getAuthorities();
        assertNotNull(grantedAuthorities);

        UserName userName2 = new UserName("hogehoge2");
        UserPassword userPassword2 = new UserPassword("hogehoge2");
        UserRole userRole2 = UserRole.ROLE_EDITOR;
        User user2 = UserFactory.create(userName2, userPassword2, userRole2);
        Collection<GrantedAuthority> grantedAuthorities2 = user2.getAuthorities();
        assertNotNull(grantedAuthorities2);

        UserName userName3 = new UserName("hogehoge3");
        UserPassword userPassword3 = new UserPassword("hogehoge3");
        UserRole userRole3 = UserRole.ROLE_ADMIN;
        User user3 = UserFactory.create(userName3, userPassword3, userRole3);
        Collection<GrantedAuthority> grantedAuthorities3 = user3.getAuthorities();
        assertNotNull(grantedAuthorities3);


    }

}
