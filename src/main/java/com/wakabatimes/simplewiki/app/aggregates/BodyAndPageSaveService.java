package com.wakabatimes.simplewiki.app.aggregates;

import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageType;
import com.wakabatimes.simplewiki.app.domain.service.body.BodyService;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BodyAndPageSaveService {
    @Autowired
    private PageService pageService;
    @Autowired
    private BodyService bodyService;

    @Transactional
    public void save(Body body, MenuId menuId, Page newPage) {
        if(newPage.getPageType().getId() == PageType.ROOT.getId()){
            pageService.updateRoot(newPage,menuId);
        }else {
            Page parent = pageService.getParent(newPage.getPageId());
            pageService.saveBranch(newPage,parent.getPageId());
        }
        bodyService.save(body,newPage.getPageId());
    }
}
