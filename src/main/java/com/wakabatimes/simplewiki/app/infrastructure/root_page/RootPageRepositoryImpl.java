package com.wakabatimes.simplewiki.app.infrastructure.root_page;

import com.wakabatimes.simplewiki.app.domain.aggregates.root_page.RootPage;
import com.wakabatimes.simplewiki.app.domain.aggregates.root_page.RootPageRepository;
import com.wakabatimes.simplewiki.app.domain.model.menu.*;
import com.wakabatimes.simplewiki.app.domain.model.page.*;
import com.wakabatimes.simplewiki.app.infrastructure.page.dto.PageDto;
import com.wakabatimes.simplewiki.app.infrastructure.root_page.dto.RootPageDto;
import com.wakabatimes.simplewiki.app.infrastructure.root_page.mapper.RootPageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RootPageRepositoryImpl implements RootPageRepository{
    @Autowired
    private RootPageMapper rootPageMapper;

    @Override
    public RootPage getByPageId(PageId pageId) {
        RootPageDto input = new RootPageDto();
        input.setPageId(pageId.getValue());
        RootPageDto result = rootPageMapper.getByPageId(input);
        if(result != null) {
            MenuId menuId = new MenuId(result.getMenuId());
            MenuName menuName = new MenuName(result.getMenuName());
            MenuLimit menuLimit = MenuLimit.getById(result.getMenuViewLimit());
            Menu menu = new Menu(menuId,menuName,menuLimit);

            PageId pageId1 = new PageId(result.getPageId());
            PageName pageName = new PageName(result.getPageName());
            PageType pageType = PageType.getById(result.getPageType());
            Page page = new Page(pageId1,pageName,pageType);

            RootPage rootPage = new RootPage(menu,page);
            return rootPage;
        }else {
            throw new RuntimeException("メニューまたはページが存在しません");
        }
    }

    @Override
    public RootPage getRootPageByName(MenuId menuId, PageName pageName) {
        RootPageDto input = new RootPageDto();
        input.setMenuId(menuId.getValue());
        input.setPageName(pageName.getValue());
        RootPageDto result = rootPageMapper.getByRootPageByName(input);

        MenuId menuId1 = new MenuId(result.getMenuId());
        MenuName menuName = new MenuName(result.getMenuName());
        MenuLimit menuLimit = MenuLimit.getById(result.getMenuViewLimit());
        MenuSortNumber menuSortNumber = new MenuSortNumber(result.getMenuSortNumber());
        Menu menu = new Menu(menuId1,menuName,menuLimit,menuSortNumber);

        PageId pageId = new PageId(result.getPageId());
        PageName pageName1 = new PageName(result.getPageName());
        PageType pageType = PageType.getById(result.getPageType());
        PageSortNumber pageSortNumber = new PageSortNumber(result.getPageSortNumber());
        Page page = new Page(pageId,pageName1,pageType,pageSortNumber);

        RootPage rootPage = new RootPage(menu,page);
        return rootPage;
    }
}
