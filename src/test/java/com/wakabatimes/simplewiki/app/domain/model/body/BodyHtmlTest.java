package com.wakabatimes.simplewiki.app.domain.model.body;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class BodyHtmlTest {
    @Test
    public void createInstance_success() {
        BodyHtml bodyHtml = new BodyHtml("hogehoge");
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_fail() {
        BodyHtml bodyHtml = new BodyHtml("");
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_fail2() {
        String str = "A";
        String rep= StringUtils.repeat(str, 1000001);
        BodyHtml bodyHtml = new BodyHtml(rep);
    }

    @Test
    public void getValue() {
        BodyHtml bodyHtml = new BodyHtml("hogehoge");

        Assert.assertEquals("hogehoge", bodyHtml.getValue());
    }

    @Test
    public void equals() {
        BodyHtml bodyHtml = new BodyHtml("hogehoge");
        BodyHtml bodyHtml2 = new BodyHtml("hogehoge");

        Assert.assertTrue(bodyHtml.equals(bodyHtml2));
    }
}
