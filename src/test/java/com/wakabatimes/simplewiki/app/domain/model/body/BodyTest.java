package com.wakabatimes.simplewiki.app.domain.model.body;

import org.junit.Assert;
import org.junit.Test;

public class BodyTest {
    @Test
    public void createInsntace_success() {
        BodyContent bodyContent = new BodyContent("hogehoge");
        Body body = BodyFactory.create(bodyContent);
        Assert.assertNotNull(body);
    }

    @Test
    public void equals() {
        BodyContent bodyContent = new BodyContent("hogehoge");
        Body body = BodyFactory.create(bodyContent);

        BodyContent bodyContent2 = new BodyContent("hogehoge");
        Body body2 = BodyFactory.create(bodyContent);

        Assert.assertFalse(body.equals(body2));
    }

    @Test
    public void canEqual() {
        BodyContent bodyContent = new BodyContent("hogehoge");
        Body body = BodyFactory.create(bodyContent);

        BodyContent bodyContent2 = new BodyContent("hogehoge");
        Body body2 = BodyFactory.create(bodyContent);

        Assert.assertTrue(body.canEqual(body2));
    }

    @Test
    public void getUserName() {
        BodyContent bodyContent = new BodyContent("hogehoge");
        Body body = BodyFactory.create(bodyContent);

        Assert.assertEquals("hogehoge", body.getBodyContent().getValue());
    }

    @Test
    public void isCurrent(){
        BodyContent bodyContent = new BodyContent("hogehoge");
        Body body = BodyFactory.create(bodyContent);
        boolean isCurrent = body.isCurrent(body);
        Assert.assertTrue(isCurrent);
    }

    @Test
    public void isNotCurrent(){
        BodyContent bodyContent = new BodyContent("hogehoge");
        Body body = BodyFactory.create(bodyContent);
        BodyId bodyId = body.getBodyId();
        Body newBody = new Body(bodyId,bodyContent,BodyType.ARCHIVE);
        boolean isCurrent = body.isCurrent(newBody);
        Assert.assertFalse(isCurrent);
    }
}
