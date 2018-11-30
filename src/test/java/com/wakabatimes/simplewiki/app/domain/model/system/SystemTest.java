package com.wakabatimes.simplewiki.app.domain.model.system;

import org.junit.Assert;
import org.junit.Test;

public class SystemTest {
    @Test
    public void createInsntace_success() {
        SystemName bodyContent = new SystemName("hogehoge");
        System body = SystemFactory.create(bodyContent);
        Assert.assertNotNull(body);
    }

    @Test
    public void getUserName() {
        SystemName bodyContent = new SystemName("hogehoge");
        System body = SystemFactory.create(bodyContent);
        Assert.assertEquals("hogehoge", body.getSystemName().getValue());
    }
}
