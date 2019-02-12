package com.wakabatimes.simplewiki.app.domain.model.menu;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum MenuLimit {
    PUBLIC(0),
    PRIVATE(1);

    @Getter
    private Integer id;

    private MenuLimit(Integer id) {
        this.id = id;
    }

    public static MenuLimit getById(Integer id) {
        for(MenuLimit menuLimit : MenuLimit.values()){
            if(menuLimit.id == id){
                return menuLimit;
            }
        }
        return MenuLimit.PUBLIC;
    }
}
