package com.wakabatimes.simplewiki.app.domain.model.menu;

import org.junit.Assert;
import org.junit.Test;

public class MenuNameTest {
    @Test
    public void createInstance_success() {
        MenuName menuName = new MenuName("hogehoge");
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_fail() {
        MenuName menuName = new MenuName("a");
    }

    @Test
    public void getValue() {
        MenuName menuName = new MenuName("hogehoge");

        Assert.assertEquals("hogehoge", menuName.getValue());
    }

    @Test
    public void equals() {
        MenuName menuName = new MenuName("hogehoge");
        MenuName menuName2 = new MenuName("hogehoge");

        Assert.assertTrue(menuName.equals(menuName2));
    }
}
