package com.wakabatimes.simplewiki.app.domain.service.page;

import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;
import com.wakabatimes.simplewiki.app.domain.model.page.PageName;
import com.wakabatimes.simplewiki.app.domain.model.page.Pages;

public interface PageService {
    /**
     * メニュー直下のページ保存
     * @param page
     * @param menuId
     */
    void saveRoot(Page page, MenuId menuId);

    /**
     * ページ配下にページ保存
     * @param child
     * @param parentId
     */
    void saveBranch(Page child, PageId parentId);

    /**
     * メニュー直下のページ情報の更新
     * @param page
     * @param menuId
     */
    void updateRoot(Page page, MenuId menuId);

    /**
     * ページ直下のページ情報の更新
     * @param page
     * @param parentId
     */
    void updateBranch(Page page, PageId parentId);

    /**
     * ページ情報の削除
     * @param page
     */
    void delete(Page page);

    /**
     * メニュー直下のページのリスト
     * @param menuId
     * @return
     */
    Pages listRoot(MenuId menuId);

    /**
     * 親ページ直下のページのリスト
     * @param parentId
     * @return
     */
    Pages listBranch(PageId parentId);

    /**
     *
     * @param pageId
     * @return
     */
    Page get(PageId pageId);

    /**
     * ページ名からのルートページの検索
     * @param pageName
     * @return
     */
    Page getRootPageByName(PageName pageName);

    /**
     * 親ページIDと子ページ名からのページの検索
     * @param parentId
     * @param pageName
     * @return
     */
    Page getPageByParentAndChildName(PageId parentId, PageName pageName);
}
