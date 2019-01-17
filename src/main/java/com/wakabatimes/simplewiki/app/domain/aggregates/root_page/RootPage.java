package com.wakabatimes.simplewiki.app.domain.aggregates.root_page;

import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import lombok.Getter;
import lombok.NonNull;

public class RootPage {
    @Getter
    @NonNull
    MenuId menuId;
    @Getter
    @NonNull
    Page page;

    public RootPage(MenuId menuId, Page page) {
        this.menuId = menuId;
        this.page = page;
    }
}
