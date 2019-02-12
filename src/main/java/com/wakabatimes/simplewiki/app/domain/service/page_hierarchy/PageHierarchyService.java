package com.wakabatimes.simplewiki.app.domain.service.page_hierarchy;

import com.wakabatimes.simplewiki.app.domain.aggregates.page_hierarchy.PageHierarchies;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;

public interface PageHierarchyService {
    /**
     * ページ階層の取得
     * @return
     */
    PageHierarchies list(MenuId menuId);

    /**
     * 現在のパスの取得
     * @param pageId
     * @return
     */
    PageHierarchies getCurrentPath(PageId pageId);
}
