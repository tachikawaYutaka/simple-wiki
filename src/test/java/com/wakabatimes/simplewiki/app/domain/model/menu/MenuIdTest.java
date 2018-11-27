package com.wakabatimes.simplewiki.app.domain.model.menu;

import org.junit.Assert;
import org.junit.Test;

public class MenuIdTest {
    @Test
    public void createInsntace_success(){
        MenuId menuId = new MenuId("a1d27ddd-d247-4e38-b9c0-f8e63b0c4145");

        Assert.assertNotNull(menuId);
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_blank_fail(){
        MenuId menuId = new MenuId("");
    }
}
