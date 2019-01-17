package com.wakabatimes.simplewiki.app.infrastructure.menu.dto;

import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import lombok.Data;

@Data
public class MenuDto {
    private String id;
    private String name;
    private Integer viewLimit;
    private Integer sortNumber;

    public MenuDto() {

    }

    public MenuDto(Menu menu){
        this.id = menu.getMenuId().getValue();
        this.name = menu.getMenuName().getValue();
        this.viewLimit = menu.getMenuLimit().getId();
    }
}
