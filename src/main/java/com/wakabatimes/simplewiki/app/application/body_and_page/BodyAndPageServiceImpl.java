package com.wakabatimes.simplewiki.app.application.body_and_page;

import com.wakabatimes.simplewiki.app.domain.aggregates.body_and_page.BodyAndPage;
import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageType;
import com.wakabatimes.simplewiki.app.domain.service.body.BodyService;
import com.wakabatimes.simplewiki.app.domain.service.body_and_page.BodyAndPageService;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BodyAndPageServiceImpl implements BodyAndPageService{
    @Autowired
    private PageService pageService;
    @Autowired
    private BodyService bodyService;

    @Override
    @Transactional
    public void save(BodyAndPage bodyAndPage) {
        Page newPage = bodyAndPage.getNewPage();
        Body body = bodyAndPage.getBody();
        MenuId menuId = bodyAndPage.getMenuId();

        if(newPage.getPageType().getId() == PageType.ROOT.getId()){
            pageService.updateRoot(newPage,menuId);
        }else {
            Page parent = pageService.getParent(newPage.getPageId());
            pageService.updateBranch(newPage,parent.getPageId());
        }
        bodyService.save(body,newPage.getPageId());
    }
}
