package com.wakabatimes.simplewiki.app.infrastructure.root_page;

import com.wakabatimes.simplewiki.app.domain.aggregates.root_page.RootPage;
import com.wakabatimes.simplewiki.app.domain.aggregates.root_page.RootPageRepository;
import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuLimit;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuName;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;
import com.wakabatimes.simplewiki.app.domain.model.page.PageName;
import com.wakabatimes.simplewiki.app.domain.model.page.PageType;
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
}
