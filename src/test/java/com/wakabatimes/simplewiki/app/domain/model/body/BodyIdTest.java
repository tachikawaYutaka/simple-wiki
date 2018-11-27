package com.wakabatimes.simplewiki.app.domain.model.body;

import org.junit.Assert;
import org.junit.Test;

public class BodyIdTest {
    @Test
    public void createInsntace_success(){
        BodyId bodyId = new BodyId("a1d27ddd-d247-4e38-b9c0-f8e63b0c4145");

        Assert.assertNotNull(bodyId);
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_blank_fail(){
        BodyId bodyId = new BodyId("");
    }
}
