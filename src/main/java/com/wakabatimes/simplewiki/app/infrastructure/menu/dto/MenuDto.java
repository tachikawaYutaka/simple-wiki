package com.wakabatimes.simplewiki.app.infrastructure.menu.dto;

import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import lombok.Data;

@Data
public class MenuDto {
    String id;
    String name;
    Integer viewLimit;

    public MenuDto() {

    }

    public MenuDto(Menu menu){
        this.id = menu.getMenuId().getValue();
        this.name = menu.getMenuName().getValue();
        this.viewLimit = menu.getMenuLimit().getId();
    }
}
