package com.wakabatimes.simplewiki.app.domain.model.original_html;

import org.junit.Assert;
import org.junit.Test;

public class OriginalHtmlTest {
    @Test
    public void createInsntace_success() {
        OriginalHtmlBody bodyContent = new OriginalHtmlBody("hogehoge");
        OriginalHtml body = OriginalHtmlFactory.create(bodyContent);
        Assert.assertNotNull(body);
    }

    @Test
    public void equals() {
        OriginalHtmlBody bodyContent = new OriginalHtmlBody("hogehoge");
        OriginalHtml body = OriginalHtmlFactory.create(bodyContent);

        OriginalHtmlBody bodyContent2 = new OriginalHtmlBody("hogehoge");
        OriginalHtml body2 = OriginalHtmlFactory.create(bodyContent2);

        Assert.assertFalse(body.equals(body2));
    }

    @Test
    public void canEqual() {
        OriginalHtmlBody bodyContent = new OriginalHtmlBody("hogehoge");
        OriginalHtml body = OriginalHtmlFactory.create(bodyContent);

        OriginalHtmlBody bodyContent2 = new OriginalHtmlBody("hogehoge");
        OriginalHtml body2 = OriginalHtmlFactory.create(bodyContent2);

        Assert.assertTrue(body.canEqual(body2));
    }

    @Test
    public void getUserName() {
        OriginalHtmlBody bodyContent = new OriginalHtmlBody("hogehoge");
        OriginalHtml body = OriginalHtmlFactory.create(bodyContent);
        Assert.assertEquals("hogehoge", body.getOriginalHtmlBody().getValue());
    }
}
