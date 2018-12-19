package com.wakabatimes.simplewiki.app.domain.model.menu;

public interface MenuRepository {

    void save(Menu menu);

    void update(Menu menu);

    void delete(Menu menu);

    Menus list();

    Menu getByMenuName(MenuName menuName);

    Menus listByMenuLimit(MenuLimit menuLimit);

    Menu getById(MenuId menuId);

    Menu getHomeMenu();
}
