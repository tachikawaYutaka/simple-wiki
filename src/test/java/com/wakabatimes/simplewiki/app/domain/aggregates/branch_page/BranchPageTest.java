package com.wakabatimes.simplewiki.app.domain.aggregates.branch_page;

import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageFactory;
import com.wakabatimes.simplewiki.app.domain.model.page.PageName;
import com.wakabatimes.simplewiki.app.domain.model.page.PageType;
import org.junit.Assert;
import org.junit.Test;

public class BranchPageTest {
    @Test
    public void createInsntace_success() {
        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        PageName pageName2 = new PageName("hogehoge");
        PageType pageType2 = PageType.BRANCH;
        Page page2 = PageFactory.create(pageName2,pageType2);

        BranchPage branchPage = new BranchPage(page.getPageId(),page2);

        Assert.assertNotNull(branchPage);
    }

    @Test
    public void equals() {
        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        PageName pageName2 = new PageName("hogehoge");
        PageType pageType2 = PageType.BRANCH;
        Page page2 = PageFactory.create(pageName2,pageType2);

        BranchPage branchPage = new BranchPage(page.getPageId(),page2);

        PageName pageName3 = new PageName("hogehoge");
        PageType pageType3 = PageType.ROOT;
        Page page3 = PageFactory.create(pageName3,pageType3);

        PageName pageName4 = new PageName("hogehoge");
        PageType pageType4 = PageType.BRANCH;
        Page page4 = PageFactory.create(pageName4,pageType4);

        BranchPage branchPage2 = new BranchPage(page3.getPageId(),page4);

        Assert.assertFalse(branchPage.equals(branchPage2));
    }

    @Test
    public void getPage() {
        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        PageName pageName2 = new PageName("hogehoge");
        PageType pageType2 = PageType.BRANCH;
        Page page2 = PageFactory.create(pageName2,pageType2);

        BranchPage branchPage = new BranchPage(page.getPageId(),page2);

        Assert.assertEquals("hogehoge", branchPage.getPage().getPageName().getValue());
    }
}
