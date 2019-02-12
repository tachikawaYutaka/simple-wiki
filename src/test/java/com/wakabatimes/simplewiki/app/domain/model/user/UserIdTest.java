package com.wakabatimes.simplewiki.app.domain.model.user;

import org.junit.Assert;
import org.junit.Test;

public class UserIdTest {
    @Test
    public void createInsntace_success(){
        UserId siteId = new UserId("a1d27ddd-d247-4e38-b9c0-f8e63b0c4145");

        Assert.assertNotNull(siteId);
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_blank_fail(){
        UserId siteId = new UserId("");
    }
}
