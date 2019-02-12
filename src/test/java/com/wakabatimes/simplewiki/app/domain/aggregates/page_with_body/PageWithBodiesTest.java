package com.wakabatimes.simplewiki.app.domain.aggregates.page_with_body;

import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyContent;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyFactory;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyHtml;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageFactory;
import com.wakabatimes.simplewiki.app.domain.model.page.PageName;
import com.wakabatimes.simplewiki.app.domain.model.page.PageType;
import com.wakabatimes.simplewiki.app.interfaces.page_with_body.dto.PageWithBodyResponseDto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PageWithBodiesTest {
    @Test
    public void add() {
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

        PageWithBodies pageWithBodies = new PageWithBodies();
        pageWithBodies.add(pageWithBody);
        pageWithBodies.add(pageWithBody2);

        assertEquals(2, pageWithBodies.size().intValue());
    }

    @Test
    public void pageWithBodies() {
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

        List<PageWithBody> pageWithBodyList = new ArrayList<>();
        pageWithBodyList.add(pageWithBody);
        pageWithBodyList.add(pageWithBody2);

        PageWithBodies pageWithBodies = new PageWithBodies(pageWithBodyList);

        assertEquals(2, pageWithBodies.size().intValue());
    }

    @Test
    public void list() {
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

        List<PageWithBody> pageWithBodyList = new ArrayList<>();
        pageWithBodyList.add(pageWithBody);
        pageWithBodyList.add(pageWithBody2);

        PageWithBodies pageWithBodies = new PageWithBodies(pageWithBodyList);

        assertEquals(pageWithBodyList, pageWithBodies.list());
    }

    @Test
    public void responseList() {
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

        List<PageWithBody> pageWithBodyList = new ArrayList<>();
        pageWithBodyList.add(pageWithBody);
        pageWithBodyList.add(pageWithBody2);

        PageWithBodies pageWithBodies = new PageWithBodies(pageWithBodyList);

        List<PageWithBodyResponseDto> pageWithBodyResponseDtos = pageWithBodies.responseList();

        assertEquals(pageWithBodyResponseDtos.get(0).getPageName(), pageWithBodies.list().get(0).getPage().getPageName().getValue());
    }
}
