package com.wakabatimes.simplewiki.app.domain.model.user;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UsersTest {
    @Test
    public void add() {
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        UserRole userRole = UserRole.ROLE_USER;
        User user = UserFactory.create(userName, userPassword, userRole);

        UserName userName2 = new UserName("hogehoge3");
        UserPassword userPassword2 = new UserPassword("hogehoge3");
        UserRole userRole2 = UserRole.ROLE_USER;
        User user2 = UserFactory.create(userName2, userPassword2, userRole2);

        Users users = new Users();
        users.add(user);
        users.add(user2);

        assertEquals(2, users.size().intValue());
    }

    @Test
    public void users() {
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        UserRole userRole = UserRole.ROLE_USER;
        User user = UserFactory.create(userName, userPassword, userRole);

        UserName userName2 = new UserName("hogehoge2");
        UserPassword userPassword2 = new UserPassword("hogehoge2");
        UserRole userRole2 = UserRole.ROLE_USER;
        User user2 = UserFactory.create(userName2, userPassword2, userRole2);

        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);
        Users users = new Users(userList);

        assertEquals(2, users.size().intValue());
    }

    @Test
    public void list() {
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        UserRole userRole = UserRole.ROLE_USER;
        User user = UserFactory.create(userName, userPassword, userRole);

        UserName userName2 = new UserName("hogehoge2");
        UserPassword userPassword2 = new UserPassword("hogehoge2");
        UserRole userRole2 = UserRole.ROLE_USER;
        User user2 = UserFactory.create(userName2, userPassword2, userRole2);

        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);
        Users users = new Users(userList);

        assertEquals(userList, users.list());
    }

    @Test(expected = RuntimeException.class)
    public void duplicate_add_to_fail_same_username() {
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        UserRole userRole = UserRole.ROLE_USER;
        User user = UserFactory.create(userName, userPassword, userRole);

        UserName userName2 = new UserName("hogehoge");
        UserPassword userPassword2 = new UserPassword("hogehoge");
        UserRole userRole2 = UserRole.ROLE_USER;
        User user2 = UserFactory.create(userName2, userPassword2, userRole2);

        Users users = new Users();
        users.add(user);
        users.add(user2);
    }

}
