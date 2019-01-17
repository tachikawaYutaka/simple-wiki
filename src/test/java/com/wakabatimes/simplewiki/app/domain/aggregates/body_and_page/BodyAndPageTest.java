package com.wakabatimes.simplewiki.app.domain.aggregates.body_and_page;

import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyContent;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyFactory;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyHtml;
import com.wakabatimes.simplewiki.app.domain.model.menu.*;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageFactory;
import com.wakabatimes.simplewiki.app.domain.model.page.PageName;
import com.wakabatimes.simplewiki.app.domain.model.page.PageType;
import org.junit.Assert;
import org.junit.Test;

public class BodyAndPageTest {
    @Test
    public void createInsntace_success() {
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        BodyContent bodyContent = new BodyContent("hogehoge");
        BodyHtml bodyHtml = new BodyHtml("hogehoge");
        Body body = BodyFactory.create(bodyContent, bodyHtml);

        BodyAndPage bodyAndPage = new BodyAndPage(menu.getMenuId(),page,body);
        Assert.assertNotNull(bodyAndPage);
    }

    @Test
    public void equals() {
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        BodyContent bodyContent = new BodyContent("hogehoge");
        BodyHtml bodyHtml = new BodyHtml("hogehoge");
        Body body = BodyFactory.create(bodyContent, bodyHtml);

        BodyAndPage bodyAndPage = new BodyAndPage(menu.getMenuId(),page,body);

        MenuName menuName2 = new MenuName("hogehoge");
        MenuLimit menuLimit2 = MenuLimit.PUBLIC;
        Menu menu2 = MenuFactory.create(menuName2,menuLimit2);

        PageName pageName2 = new PageName("hogehoge");
        PageType pageType2 = PageType.ROOT;
        Page page2 = PageFactory.create(pageName2,pageType2);

        BodyContent bodyContent2 = new BodyContent("hogehoge");
        BodyHtml bodyHtml2 = new BodyHtml("hogehoge");
        Body body2 = BodyFactory.create(bodyContent2, bodyHtml2);

        BodyAndPage bodyAndPage2 = new BodyAndPage(menu2.getMenuId(),page2,body2);

        Assert.assertFalse(bodyAndPage.equals(bodyAndPage2));
    }

    @Test
    public void getPageAndBody() {
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        BodyContent bodyContent = new BodyContent("hogehoge");
        BodyHtml bodyHtml = new BodyHtml("hogehoge");
        Body body = BodyFactory.create(bodyContent, bodyHtml);

        BodyAndPage bodyAndPage = new BodyAndPage(menu.getMenuId(),page,body);

        Assert.assertEquals("hogehoge", bodyAndPage.getNewPage().getPageName().getValue());
        Assert.assertEquals("hogehoge", bodyAndPage.getBody().getBodyContent().getValue());
    }

}
