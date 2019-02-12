package com.wakabatimes.simplewiki.app.domain.aggregates.main_menu;

import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.page.Pages;
import lombok.Getter;
import lombok.NonNull;

public class MainMenu {
    @Getter
    @NonNull
    Menu menu;
    @Getter
    @NonNull
    Pages pages;

    public MainMenu(Menu menu, Pages pages) {
        this.menu = menu;
        this.pages = pages;
    }
}
