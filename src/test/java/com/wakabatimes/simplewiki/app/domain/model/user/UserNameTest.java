package com.wakabatimes.simplewiki.app.domain.model.user;

import org.junit.Assert;
import org.junit.Test;

public class UserNameTest {
    @Test
    public void createInstance_success() {
        UserName userName = new UserName("hogehoge");
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_fail() {
        UserName userName = new UserName("a");
    }

    @Test
    public void getValue() {
        UserName userName = new UserName("hogehoge");

        Assert.assertEquals("hogehoge", userName.getValue());
    }

    @Test
    public void equals() {
        UserName userName = new UserName("hogehoge");
        UserName userName2 = new UserName("hogehoge");

        Assert.assertTrue(userName.equals(userName2));
    }
}
