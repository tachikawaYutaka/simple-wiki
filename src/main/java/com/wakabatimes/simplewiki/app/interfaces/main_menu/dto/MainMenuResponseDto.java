package com.wakabatimes.simplewiki.app.interfaces.main_menu.dto;

import com.wakabatimes.simplewiki.app.domain.aggregates.main_menu.MainMenu;
import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.interfaces.page.dto.PageResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class MainMenuResponseDto {
    private String menuId;
    private String menuName;
    private String menuLimit;
    private Integer menuSortNumber;
    private List<PageResponseDto> pages;

    public MainMenuResponseDto(MainMenu mainMenu) {
        this.menuId = mainMenu.getMenu().getMenuId().getValue();
        this.menuName = mainMenu.getMenu().getMenuName().getValue();
        this.menuLimit = mainMenu.getMenu().getMenuLimit().name();
        this.menuSortNumber = mainMenu.getMenu().getMenuSortNumber().getValue();
    }
}
