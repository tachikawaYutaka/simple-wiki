package com.wakabatimes.simplewiki.app.domain.aggregates.body_and_page;

import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import lombok.Getter;
import lombok.NonNull;

public class BodyAndPage {
    @Getter
    @NonNull
    MenuId menuId;
    @Getter
    @NonNull
    Page newPage;
    @Getter
    @NonNull
    Body body;

    public BodyAndPage(MenuId menuId, Page newPage, Body body) {
        this.menuId = menuId;
        this.newPage = newPage;
        this.body = body;
    }
}
