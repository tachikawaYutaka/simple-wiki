package com.wakabatimes.simplewiki.app.application.root_page;

import com.wakabatimes.simplewiki.app.domain.aggregates.root_page.RootPage;
import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyFactory;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.service.body.BodyService;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import com.wakabatimes.simplewiki.app.domain.service.root_page.RootPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RootPageServiceImpl implements RootPageService{
    @Autowired
    private PageService pageService;
    @Autowired
    private BodyService bodyService;

    @Override
    @Transactional
    public void save(RootPage rootPage) {
        pageService.saveRoot(rootPage.getPage(),rootPage.getMenuId());
        Body body = BodyFactory.createNewBody();
        bodyService.save(body,rootPage.getPage().getPageId());
    }
}
