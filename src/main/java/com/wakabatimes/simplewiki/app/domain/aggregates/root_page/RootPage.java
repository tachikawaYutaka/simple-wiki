package com.wakabatimes.simplewiki.app.domain.aggregates.root_page;

import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import lombok.Getter;
import lombok.NonNull;

public class RootPage {
    @Getter
    @NonNull
    Menu menu;
    @Getter
    @NonNull
    Page page;

    public RootPage(Menu menu, Page page) {
        this.menu = menu;
        this.page = page;
    }
}
