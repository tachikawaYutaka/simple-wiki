package com.wakabatimes.simplewiki.app.domain.aggregates.page_hierarchy;

import com.wakabatimes.simplewiki.app.domain.model.page.*;
import org.junit.Assert;
import org.junit.Test;

public class PageHierarchyTest {
    @Test
    public void createInsntace_success() {
        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        PagePath pagePath = new PagePath("/hogehoge/");

        PageHierarchies pageHierarchies = new PageHierarchies();

        PageHierarchy pageHierarchy = new PageHierarchy(page,pagePath,pageHierarchies);

        Assert.assertNotNull(pageHierarchy);
    }

    @Test
    public void equals() {
        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        PagePath pagePath = new PagePath("/hogehoge/");

        PageHierarchies pageHierarchies = new PageHierarchies();

        PageHierarchy pageHierarchy = new PageHierarchy(page,pagePath,pageHierarchies);

        PageName pageName2 = new PageName("hogehoge");
        PageType pageType2 = PageType.ROOT;
        Page page2 = PageFactory.create(pageName2,pageType2);

        PagePath pagePath2 = new PagePath("/hogehoge/");

        PageHierarchies pageHierarchies2 = new PageHierarchies();

        PageHierarchy pageHierarchy2 = new PageHierarchy(page2,pagePath2,pageHierarchies2);

        Assert.assertFalse(pageHierarchy.equals(pageHierarchy2));
    }

    @Test
    public void getPage() {
        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        PagePath pagePath = new PagePath("/hogehoge/");

        PageHierarchies pageHierarchies = new PageHierarchies();

        PageHierarchy pageHierarchy = new PageHierarchy(page,pagePath,pageHierarchies);

        Assert.assertEquals("hogehoge", pageHierarchy.getPage().getPageName().getValue());
    }
}
