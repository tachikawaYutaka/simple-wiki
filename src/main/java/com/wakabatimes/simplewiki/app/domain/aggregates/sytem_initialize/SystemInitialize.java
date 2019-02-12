package com.wakabatimes.simplewiki.app.domain.aggregates.sytem_initialize;

import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.model.user.User;
import lombok.Getter;
import lombok.NonNull;

public class SystemInitialize {
    @Getter
    @NonNull
    System system;
    @Getter
    @NonNull
    User user;
    @Getter
    @NonNull
    Menu menu;
    @Getter
    @NonNull
    Page page;
    @Getter
    @NonNull
    Body body;

    public SystemInitialize(System system, User user, Menu menu, Page page, Body body){
        this.system = system;
        this.user = user;
        this.menu = menu;
        this.page = page;
        this.body = body;
    }
}
