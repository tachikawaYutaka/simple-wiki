package com.wakabatimes.simplewiki.app.domain.model.user;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UsersTest {
    @Test
    public void add() {
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");

        User user = UserFactory.create(userName, userPassword);

        UserName userName2 = new UserName("hogehoge2");
        UserPassword userPassword2 = new UserPassword("hogehoge");

        User user2 = UserFactory.create(userName2, userPassword2);

        Users users = new Users();
        users.add(user);
        users.add(user2);

        assertEquals(2, users.size().intValue());
    }

    @Test(expected = RuntimeException.class)
    public void duplicate_add_to_fail_same_username() {
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");

        User user = UserFactory.create(userName, userPassword);

        UserName userName2 = new UserName("hogehoge");
        UserPassword userPassword2 = new UserPassword("hogehoge");

        User user2 = UserFactory.create(userName2, userPassword2);

        Users users = new Users();
        users.add(user);
        users.add(user2);
    }

}
