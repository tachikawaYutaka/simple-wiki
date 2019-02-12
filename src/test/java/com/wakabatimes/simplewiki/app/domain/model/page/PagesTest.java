package com.wakabatimes.simplewiki.app.domain.model.page;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void pages() {
        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.ROOT;
        Page page2 = PageFactory.create(pageName2,pageType2);

        List<Page> pageList = new ArrayList<>();
        pageList.add(page);
        pageList.add(page2);

        Pages pages = new Pages(pageList);

        assertEquals(2, pages.size().intValue());
    }

    @Test
    public void list() {
        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.ROOT;
        Page page2 = PageFactory.create(pageName2,pageType2);

        List<Page> pageList = new ArrayList<>();
        pageList.add(page);
        pageList.add(page2);

        Pages pages = new Pages(pageList);

        assertEquals(pageList, pages.list());
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
