package com.wakabatimes.simplewiki.app.aggregates;

import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.model.user.User;
import com.wakabatimes.simplewiki.app.domain.service.body.BodyService;
import com.wakabatimes.simplewiki.app.domain.service.menu.MenuService;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import com.wakabatimes.simplewiki.app.domain.service.system.SystemService;
import com.wakabatimes.simplewiki.app.domain.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SystemInitializeService {
    @Autowired
    private UserService userService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private PageService pageService;
    @Autowired
    private BodyService bodyService;

    @Transactional
    public void save(System system, User user, Menu menu, Page page, Body body){
        systemService.save(system);
        userService.save(user);
        menuService.save(menu);
        pageService.saveRoot(page, menu.getMenuId());
        bodyService.save(body,page.getPageId());
    }
}
