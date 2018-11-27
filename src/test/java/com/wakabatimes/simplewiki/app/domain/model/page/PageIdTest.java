package com.wakabatimes.simplewiki.app.domain.model.page;

import org.junit.Assert;
import org.junit.Test;

public class PageIdTest {
    @Test
    public void createInsntace_success(){
        PageId pageId = new PageId("a1d27ddd-d247-4e38-b9c0-f8e63b0c4145");

        Assert.assertNotNull(pageId);
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_blank_fail(){
        PageId pageId = new PageId("");
    }
}
