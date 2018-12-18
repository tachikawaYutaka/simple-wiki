package com.wakabatimes.simplewiki.app.interfaces.main_menu.dto;

import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.interfaces.page.dto.PageResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class MainMenuResponseDto {
    String menuId;
    String menuName;
    String menuLimit;
    List<PageResponseDto> pages;

    public MainMenuResponseDto(){

    }

    public MainMenuResponseDto(Menu menu) {
        this.menuId = menu.getMenuId().getValue();
        this.menuName = menu.getMenuName().getValue();
        this.menuLimit = menu.getMenuLimit().name();
    }
}
