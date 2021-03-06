package com.wakabatimes.simplewiki.app.domain.model.body;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class BodyContentTest {
    @Test
    public void createInstance_success() {
        BodyContent bodyContent = new BodyContent("hogehoge");
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_fail() {
        BodyContent bodyContent = new BodyContent("");
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_fail2() {
        String str = "A";
        String rep= StringUtils.repeat(str, 1000001);
        BodyContent bodyContent = new BodyContent(rep);
    }

    @Test
    public void getValue() {
        BodyContent bodyContent = new BodyContent("hogehoge");

        Assert.assertEquals("hogehoge", bodyContent.getValue());
    }

    @Test
    public void equals() {
        BodyContent bodyContent = new BodyContent("hogehoge");
        BodyContent bodyContent2 = new BodyContent("hogehoge");

        Assert.assertTrue(bodyContent.equals(bodyContent2));
    }
}
