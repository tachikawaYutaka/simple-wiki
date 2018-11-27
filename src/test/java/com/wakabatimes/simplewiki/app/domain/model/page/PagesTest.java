package com.wakabatimes.simplewiki.app.domain.model.page;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PagesTest {
    @Test
    public void add() {
        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.ROOT;
        Page page2 = PageFactory.create(pageName2,pageType2);

        Pages pages = new Pages();
        pages.add(page);
        pages.add(page2);

        assertEquals(2, pages.size().intValue());
    }

    @Test(expected = RuntimeException.class)
    public void duplicate_add_to_fail_same_username() {
        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        PageName pageName2 = new PageName("hogehoge");
        PageType pageType2 = PageType.ROOT;
        Page page2 = PageFactory.create(pageName2,pageType2);

        Pages pages = new Pages();
        pages.add(page);
        pages.add(page2);
    }
}
