package com.wakabatimes.simplewiki.app.domain.model.page;

import org.junit.Assert;
import org.junit.Test;

public class PageTest {
    @Test
    public void createInsntace_success() {
        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        Assert.assertNotNull(page);
    }

    @Test
    public void createInsntace_success2() {
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.createNewPage(pageType);
        Assert.assertNotNull(page);
    }

    @Test
    public void createInsntace_success3() {
        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        PageSortNumber pageSortNumber = new PageSortNumber(1);
        Page page2 = new Page(page.getPageId(),page.getPageName(),page.getPageType(),pageSortNumber);
        Assert.assertNotNull(page2);
    }

    @Test
    public void equals() {
        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        PageName pageName2 = new PageName("hogehoge");
        PageType pageType2 = PageType.ROOT;
        Page page2 = PageFactory.create(pageName2,pageType2);

        Assert.assertFalse(page.equals(page2));
    }

    @Test
    public void canEqual() {
        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        PageName pageName2 = new PageName("hogehoge");
        PageType pageType2 = PageType.ROOT;
        Page page2 = PageFactory.create(pageName2,pageType2);

        Assert.assertTrue(page.canEqual(page2));
    }

    @Test
    public void getUserName() {
        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        Assert.assertEquals("hogehoge", page.getPageName().getValue());
    }

    @Test
    public void isRoot() {
        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        boolean isRoot = page.isRoot(page);
        Assert.assertTrue(isRoot);
    }

    @Test
    public void isNotRoot() {
        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.BRANCH;
        Page page = PageFactory.create(pageName,pageType);
        boolean isRoot = page.isRoot(page);
        Assert.assertFalse(isRoot);
    }
}
