package com.wakabatimes.simplewiki.app.application.page;

import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;
import com.wakabatimes.simplewiki.app.domain.model.page.PageRepository;
import com.wakabatimes.simplewiki.app.domain.model.page.Pages;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import org.springframework.beans.factory.annotation.Autowired;

public class PageServiceImpl implements PageService {
    @Autowired
    PageRepository pageRepository;

    @Override
    public void save(Page page, MenuId menuId) {
        pageRepository.save(page,menuId);
    }

    @Override
    public void save(Page child, PageId parentId) {
        pageRepository.save(child,parentId);
    }

    @Override
    public void update(Page page, MenuId menuId) {
        pageRepository.update(page, menuId);
    }

    @Override
    public void update(Page page, PageId parentId) {
        pageRepository.update(page, parentId);
    }

    @Override
    public void delete(Page page) {
        pageRepository.delete(page);
    }

    @Override
    public Pages list(MenuId menuId) {
        return pageRepository.listByMenuId(menuId);
    }

    @Override
    public Pages list(PageId parentId) {
        return null;
    }

    @Override
    public Page get(PageId pageId) {
        return null;
    }
}
