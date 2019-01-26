package com.wakabatimes.simplewiki.app.domain.aggregates.page_with_body;

import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyContent;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyFactory;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyHtml;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageFactory;
import com.wakabatimes.simplewiki.app.domain.model.page.PageName;
import com.wakabatimes.simplewiki.app.domain.model.page.PageType;
import org.junit.Assert;
import org.junit.Test;

public class PageWithBodyTest {
    @Test
    public void createInsntace_success() {
        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        BodyContent bodyContent = new BodyContent("hogehoge");
        BodyHtml bodyHtml = new BodyHtml("hogehoge");
        Body body = BodyFactory.create(bodyContent, bodyHtml);

        PageWithBody pageWithBody = new PageWithBody(page,body);
        Assert.assertNotNull(pageWithBody);
    }

    @Test
    public void equals() {
        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        BodyContent bodyContent = new BodyContent("hogehoge");
        BodyHtml bodyHtml = new BodyHtml("hogehoge");
        Body body = BodyFactory.create(bodyContent, bodyHtml);

        PageWithBody pageWithBody = new PageWithBody(page,body);

        PageName pageName2 = new PageName("hogehoge");
        PageType pageType2 = PageType.ROOT;
        Page page2 = PageFactory.create(pageName2,pageType2);

        BodyContent bodyContent2 = new BodyContent("hogehoge");
        BodyHtml bodyHtml2 = new BodyHtml("hogehoge");
        Body body2 = BodyFactory.create(bodyContent2, bodyHtml2);

        PageWithBody pageWithBody2 = new PageWithBody(page2,body2);

        Assert.assertFalse(pageWithBody.equals(pageWithBody2));
    }

    @Test
    public void getPageAndBody() {
        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        BodyContent bodyContent = new BodyContent("hogehoge");
        BodyHtml bodyHtml = new BodyHtml("hogehoge");
        Body body = BodyFactory.create(bodyContent, bodyHtml);

        PageWithBody pageWithBody = new PageWithBody(page,body);

        Assert.assertEquals("hogehoge", pageWithBody.getPage().getPageName().getValue());
        Assert.assertEquals("hogehoge", pageWithBody.getBody().getBodyContent().getValue());
    }
}
