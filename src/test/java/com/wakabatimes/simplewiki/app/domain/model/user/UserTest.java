package com.wakabatimes.simplewiki.app.domain.model.user;

import org.junit.Assert;
import org.junit.Test;

public class UserTest {
    @Test
    public void createInsntace_success() {
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");

        User user = UserFactory.create(userName, userPassword);

        Assert.assertNotNull(user);
    }

    @Test
    public void equals() {
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");

        User user = UserFactory.create(userName, userPassword);

        UserName userName2 = new UserName("hogehoge");
        UserPassword userPassword2 = new UserPassword("hogehoge");

        User user2 = UserFactory.create(userName2, userPassword2);

        Assert.assertFalse(user.equals(user2));
    }

    @Test
    public void canEqual() {
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");

        User user = UserFactory.create(userName, userPassword);

        UserName userName2 = new UserName("hogehoge");
        UserPassword userPassword2 = new UserPassword("hogehoge");

        User user2 = UserFactory.create(userName2, userPassword2);

        Assert.assertTrue(user.canEqual(user2));
    }

    @Test
    public void getUserName() {
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");

        User user = UserFactory.create(userName, userPassword);

        Assert.assertEquals("hogehoge", user.getUserName().getValue());
    }
}
