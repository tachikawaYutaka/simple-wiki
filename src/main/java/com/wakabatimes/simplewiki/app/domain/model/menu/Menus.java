package com.wakabatimes.simplewiki.app.domain.model.menu;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Menus {
    List<Menu> menus;

    public Menus () {
        menus = new ArrayList<>();
    }

    public Menus(List<Menu> menus) {
        this.menus = new ArrayList<>(menus);
    }

    /**
     * menu add
     * @param menu
     * @throws RuntimeException
     */
    public void add(@NonNull Menu menu) throws RuntimeException {
        if(this.containsName(menu)) {
            throw new DuplicatedMenuException(menu.toString());
        }
        this.menus.add(menu);
    }

    /**
     * users count
     * @return Integer
     */
    public Integer size() {
        return this.menus.size();
    }

    /**
     * containName
     * @param compare_menu
     * @return Boolean
     */
    public boolean containsName(Menu compare_menu) {
        for(Menu menu : this.menus) {
            if (menu.getMenuName().equals(compare_menu.getMenuName()) && !menu.getMenuId().equals(compare_menu.getMenuId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * get list
     *
     * @return List<User>
     */
    public List<Menu> list() {
        return Collections.unmodifiableList(this.menus);
    }
}
