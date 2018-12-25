package com.wakabatimes.simplewiki.app.domain.model.page;

import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;

public interface PageRepository {
    void save(Page page, MenuId menuId);

    void save(Page child, PageId parentId);

    void update(Page page, MenuId menuId);

    void update(Page page, PageId parentId);

    void delete(Page page, MenuId menuId);

    Pages listByMenuId(MenuId menuId);

    Pages listByParentPageId(PageId pageId);

    Page getByPageId(PageId pageId);

    Page getRootPageByName(PageName pageName);

    Page getPageByParentAndChildName(PageId parentId, PageName pageName);

    Page getHomePage(MenuId menuId);

    Page getParent(PageId pageId);
}
