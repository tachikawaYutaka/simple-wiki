package com.wakabatimes.simplewiki.app.domain.model.page;

import org.junit.Assert;
import org.junit.Test;

public class PagePathTest {
    @Test
    public void createInsntace_success(){
        PagePath pagePath = new PagePath("hoge");

        Assert.assertNotNull(pagePath);
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_blank_fail(){
        PagePath pagePath = new PagePath("");
    }
}
