package com.wakabatimes.simplewiki.app.domain.model.page;

import org.junit.Assert;
import org.junit.Test;

public class PageNameTest {
    @Test
    public void createInstance_success() {
        PageName pageName = new PageName("hogehoge");
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_fail() {
        PageName pageName = new PageName("a");
    }

    @Test
    public void getValue() {
        PageName pageName = new PageName("hogehoge");

        Assert.assertEquals("hogehoge", pageName.getValue());
    }

    @Test
    public void equals() {
        PageName pageName = new PageName("hogehoge");
        PageName pageName2 = new PageName("hogehoge");

        Assert.assertTrue(pageName.equals(pageName2));
    }
}
