package com.wakabatimes.simplewiki.app.application.page;

import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.*;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageServiceImpl implements PageService {
    @Autowired
    PageRepository pageRepository;

    @Override
    public void saveRoot(Page page, MenuId menuId) {
        pageRepository.save(page,menuId);
    }

    @Override
    public void saveBranch(Page child, PageId parentId) {
        pageRepository.save(child,parentId);
    }

    @Override
    public void updateRoot(Page page, MenuId menuId) {
        pageRepository.update(page, menuId);
    }

    @Override
    public void updateBranch(Page page, PageId parentId) {
        pageRepository.update(page, parentId);
    }

    @Override
    public void delete(Page page) {
        pageRepository.delete(page);
    }

    @Override
    public Pages listRoot(MenuId menuId) {
        return pageRepository.listByMenuId(menuId);
    }

    @Override
    public Pages listBranch(PageId parentId) {
        return pageRepository.listByParentPageId(parentId);
    }

    @Override
    public Page get(PageId pageId) {
        return pageRepository.getByPageId(pageId);
    }

    @Override
    public Page getRootPageByName(PageName pageName) {
        return pageRepository.getRootPageByName(pageName);
    }

    @Override
    public Page getPageByParentAndChildName(PageId parentId, PageName pageName) {
        return pageRepository.getPageByParentAndChildName(parentId, pageName);
    }

    @Override
    public Page getHomePage(MenuId menuId) {
        return pageRepository.getHomePage(menuId);
    }

    @Override
    public Page getParent(PageId pageId) {
        return pageRepository.getParent(pageId);
    }
}
