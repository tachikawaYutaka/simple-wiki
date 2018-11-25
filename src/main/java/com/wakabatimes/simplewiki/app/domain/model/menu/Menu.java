package com.wakabatimes.simplewiki.app.domain.model.menu;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * root entity
 */
@Slf4j
@EqualsAndHashCode
public class Menu {
    @Getter
    @NonNull
    MenuId menuId;

    @Getter
    @NonNull
    MenuName menuName;

    @Getter
    @NonNull
    MenuLimit menuLimit;

    public Menu(MenuId menuId, MenuName menuName, MenuLimit menuLimit) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuLimit = menuLimit;
    }
}
