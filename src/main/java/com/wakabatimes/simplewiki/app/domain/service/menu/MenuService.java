package com.wakabatimes.simplewiki.app.domain.service.menu;

import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuLimit;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuName;
import com.wakabatimes.simplewiki.app.domain.model.menu.Menus;

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
     * Limitによるフィルター
     * @return
     */
    Menus listByMenuLimit(MenuLimit menuLimit);
}
