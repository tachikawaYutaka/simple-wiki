package com.wakabatimes.simplewiki.app.domain.model.menu;

import java.util.UUID;

public class MenuFactory {
    public static Menu create(MenuName menuName, MenuLimit menuLimit){
        MenuId menuId = new MenuId(UUID.randomUUID().toString());
        return new Menu(menuId, menuName, menuLimit);
    }
}
