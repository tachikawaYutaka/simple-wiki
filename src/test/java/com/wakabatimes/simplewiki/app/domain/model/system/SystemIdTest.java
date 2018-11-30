package com.wakabatimes.simplewiki.app.domain.model.system;

import org.junit.Assert;
import org.junit.Test;

public class SystemIdTest {
    @Test
    public void createInsntace_success(){
        SystemId id = new SystemId("a1d27ddd-d247-4e38-b9c0-f8e63b0c4145");

        Assert.assertNotNull(id);
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_blank_fail(){
        SystemId id = new SystemId("");
    }
}
