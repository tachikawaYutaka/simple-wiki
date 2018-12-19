package com.wakabatimes.simplewiki.app.domain.service.menu;

import com.wakabatimes.simplewiki.app.domain.model.menu.*;

public interface MenuService {
    /**
     * menuの保存
     * @param menu
     */
    void save(Menu menu);

    /**
     * menuの更新
     * @param menu
     */
    void update(Menu menu);

    /**
     * menuの削除
     * @param menu
     */
    void delete(Menu menu);

    /**
     * menuのリスト
     * @return
     */
    Menus list();

    /**
     * menuの照会
     * @param menuName
     * @return
     */
    Menu get(MenuName menuName);

    /**
     * menuの照会
     * @param menuId
     * @return
     */
    Menu getById(MenuId menuId);

    /**
     * Limitによるフィルター
     * @return
     */
    Menus listByMenuLimit(MenuLimit menuLimit);

    /**
     * menu sort 1 を紹介
     * @return
     */
    Menu getHomeMenu();
}
