package com.wakabatimes.simplewiki.app.domain.model.menu;

import java.util.UUID;

public class MenuFactory {
    public static Menu create(MenuName menuName, MenuLimit menuLimit){
        MenuId menuId = new MenuId(UUID.randomUUID().toString());
        return new Menu(menuId, menuName, menuLimit);
    }
    public static Menu createWithSort(MenuName menuName, MenuLimit menuLimit, MenuSortNumber menuSortNumber){
        MenuId menuId = new MenuId(UUID.randomUUID().toString());
        return new Menu(menuId, menuName, menuLimit, menuSortNumber);
    }
}
