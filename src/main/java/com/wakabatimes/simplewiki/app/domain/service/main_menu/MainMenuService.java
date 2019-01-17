package com.wakabatimes.simplewiki.app.domain.service.main_menu;

import com.wakabatimes.simplewiki.app.domain.aggregates.main_menu.MainMenus;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuLimit;

public interface MainMenuService {
    /**
     * メインメニューの参照
     * @return
     */
    MainMenus list();

    /**
     * 制限を指定したメインメニューの参照
     * @param menuLimit
     * @return
     */
    MainMenus listByMenuLimit(MenuLimit menuLimit);
}
