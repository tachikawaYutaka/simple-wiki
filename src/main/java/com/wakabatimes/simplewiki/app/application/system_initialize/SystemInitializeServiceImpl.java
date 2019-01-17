package com.wakabatimes.simplewiki.app.application.system_initialize;

import com.wakabatimes.simplewiki.app.domain.aggregates.sytem_initialize.SystemInitialize;
import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.model.user.User;
import com.wakabatimes.simplewiki.app.domain.service.body.BodyService;
import com.wakabatimes.simplewiki.app.domain.service.menu.MenuService;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import com.wakabatimes.simplewiki.app.domain.service.system.SystemService;
import com.wakabatimes.simplewiki.app.domain.service.system_initialize.SystemInitializeService;
import com.wakabatimes.simplewiki.app.domain.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SystemInitializeServiceImpl implements SystemInitializeService{
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

    @Override
    @Transactional
    public void save(SystemInitialize systemInitialize) {
        systemService.save(systemInitialize.getSystem());
        userService.save(systemInitialize.getUser());
        menuService.save(systemInitialize.getMenu());
        pageService.saveRoot(systemInitialize.getPage(), systemInitialize.getMenu().getMenuId());
        bodyService.save(systemInitialize.getBody(),systemInitialize.getPage().getPageId());
    }
}
