package com.wakabatimes.simplewiki.app.domain.service.page;

import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.*;

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
    void delete(Page page, MenuId menuId);

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
     * 親ページIDと子ページ名からのページの検索
     * @param parentId
     * @param pageName
     * @return
     */
    Page getPageByParentAndChildName(PageId parentId, PageName pageName);

    /**
     * menuのsort1のrootページ
     * @param menuId
     * @return
     */
    Page getHomePage(MenuId menuId);

    /**
     * 親ページを照会
     * @param pageId
     * @return
     */
    Page getParent(PageId pageId);

}
