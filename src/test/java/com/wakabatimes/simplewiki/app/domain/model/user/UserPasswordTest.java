package com.wakabatimes.simplewiki.app.domain.model.user;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserPasswordTest {
    @Test
    public void createInstance_success() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        UserPassword userPassword = new UserPassword("hogehoge", bCryptPasswordEncoder);
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_fail() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        UserPassword userPassword = new UserPassword("a",bCryptPasswordEncoder);
    }

    @Test
    public void getValue() {
        UserPassword userPassword = new UserPassword("hogehoge");

        Assert.assertEquals("hogehoge", userPassword.getValue());
    }
}
