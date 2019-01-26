package com.wakabatimes.simplewiki.app.domain.aggregates.page_hierarchy;

import com.wakabatimes.simplewiki.app.domain.model.page.*;
import com.wakabatimes.simplewiki.app.interfaces.page_hierarchy.dto.PageHierarchyResponseDto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PageHierarchiesTest {
    @Test
    public void add() {
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

        PageHierarchies pageHierarchies3 = new PageHierarchies();
        pageHierarchies3.add(pageHierarchy);
        pageHierarchies3.add(pageHierarchy2);

        assertEquals(2, pageHierarchies3.size().intValue());
    }

    @Test
    public void pageHierarchies() {
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

        List<PageHierarchy> pageHierarchyList = new ArrayList<>();
        pageHierarchyList.add(pageHierarchy);
        pageHierarchyList.add(pageHierarchy2);

        PageHierarchies pageHierarchies3 = new PageHierarchies(pageHierarchyList);

        assertEquals(2, pageHierarchies3.size().intValue());
    }

    @Test
    public void list() {
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

        List<PageHierarchy> pageHierarchyList = new ArrayList<>();
        pageHierarchyList.add(pageHierarchy);
        pageHierarchyList.add(pageHierarchy2);

        PageHierarchies pageHierarchies3 = new PageHierarchies(pageHierarchyList);

        assertEquals(pageHierarchyList, pageHierarchies3.list());
    }

    @Test
    public void responseList() {
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

        List<PageHierarchy> pageHierarchyList = new ArrayList<>();
        pageHierarchyList.add(pageHierarchy);
        pageHierarchyList.add(pageHierarchy2);

        PageHierarchies pageHierarchies3 = new PageHierarchies(pageHierarchyList);

        List<PageHierarchyResponseDto> pageHierarchyResponseDtos = pageHierarchies3.responseList();

        assertEquals(pageHierarchyResponseDtos.get(0).getName(), pageHierarchies3.list().get(0).getPage().getPageName().getValue());
    }
}
