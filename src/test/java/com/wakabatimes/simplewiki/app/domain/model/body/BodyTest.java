package com.wakabatimes.simplewiki.app.domain.model.body;

import org.junit.Assert;
import org.junit.Test;

public class BodyTest {
    @Test
    public void createInsntace_success() {
        BodyContent bodyContent = new BodyContent("hogehoge");
        BodyHtml bodyHtml = new BodyHtml("hogehoge");
        Body body = BodyFactory.create(bodyContent, bodyHtml);
        Assert.assertNotNull(body);
    }

    @Test
    public void equals() {
        BodyContent bodyContent = new BodyContent("hogehoge");
        BodyHtml bodyHtml = new BodyHtml("hogehoge");
        Body body = BodyFactory.create(bodyContent, bodyHtml);

        BodyContent bodyContent2 = new BodyContent("hogehoge");
        BodyHtml bodyHtml2 = new BodyHtml("hogehoge");
        Body body2 = BodyFactory.create(bodyContent2, bodyHtml2);

        Assert.assertFalse(body.equals(body2));
    }

    @Test
    public void canEqual() {
        BodyContent bodyContent = new BodyContent("hogehoge");
        BodyHtml bodyHtml = new BodyHtml("hogehoge");
        Body body = BodyFactory.create(bodyContent, bodyHtml);

        BodyContent bodyContent2 = new BodyContent("hogehoge");
        BodyHtml bodyHtml2 = new BodyHtml("hogehoge");
        Body body2 = BodyFactory.create(bodyContent2, bodyHtml2);

        Assert.assertTrue(body.canEqual(body2));
    }

    @Test
    public void getUserName() {
        BodyContent bodyContent = new BodyContent("hogehoge");
        BodyHtml bodyHtml = new BodyHtml("hogehoge");
        Body body = BodyFactory.create(bodyContent, bodyHtml);

        Assert.assertEquals("hogehoge", body.getBodyContent().getValue());
    }

    @Test
    public void isCurrent(){
        BodyContent bodyContent = new BodyContent("hogehoge");
        BodyHtml bodyHtml = new BodyHtml("hogehoge");
        Body body = BodyFactory.create(bodyContent, bodyHtml);
        boolean isCurrent = body.isCurrent(body);
        Assert.assertTrue(isCurrent);
    }

    @Test
    public void isNotCurrent(){
        BodyContent bodyContent = new BodyContent("hogehoge");
        BodyHtml bodyHtml = new BodyHtml("hogehoge");
        Body body = BodyFactory.create(bodyContent, bodyHtml);
        BodyId bodyId = body.getBodyId();
        Body newBody = new Body(bodyId,bodyContent, bodyHtml,BodyType.ARCHIVE);
        boolean isCurrent = body.isCurrent(newBody);
        Assert.assertFalse(isCurrent);
    }
}
