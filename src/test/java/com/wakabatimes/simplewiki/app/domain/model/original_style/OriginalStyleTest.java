package com.wakabatimes.simplewiki.app.domain.model.original_style;

import org.junit.Assert;
import org.junit.Test;

public class OriginalStyleTest {
    @Test
    public void createInsntace_success() {
        OriginalStyleBody bodyContent = new OriginalStyleBody("hogehoge");
        OriginalStyle body = OriginalStyleFactory.create(bodyContent);
        Assert.assertNotNull(body);
    }

    @Test
    public void equals() {
        OriginalStyleBody bodyContent = new OriginalStyleBody("hogehoge");
        OriginalStyle body = OriginalStyleFactory.create(bodyContent);

        OriginalStyleBody bodyContent2 = new OriginalStyleBody("hogehoge");
        OriginalStyle body2 = OriginalStyleFactory.create(bodyContent2);

        Assert.assertFalse(body.equals(body2));
    }

    @Test
    public void canEqual() {
        OriginalStyleBody bodyContent = new OriginalStyleBody("hogehoge");
        OriginalStyle body = OriginalStyleFactory.create(bodyContent);

        OriginalStyleBody bodyContent2 = new OriginalStyleBody("hogehoge");
        OriginalStyle body2 = OriginalStyleFactory.create(bodyContent2);

        Assert.assertTrue(body.canEqual(body2));
    }

    @Test
    public void getUserName() {
        OriginalStyleBody bodyContent = new OriginalStyleBody("hogehoge");
        OriginalStyle body = OriginalStyleFactory.create(bodyContent);
        Assert.assertEquals("hogehoge", body.getOriginalStyleBody().getValue());
    }
}
