package com.wakabatimes.simplewiki.app.aggregates;

import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyContent;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyFactory;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyHtml;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuId;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.service.body.BodyService;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RootPageCreateService {
    @Autowired
    private PageService pageService;
    @Autowired
    private BodyService bodyService;

    @Transactional
    public void save(Page page, MenuId menuId){
        pageService.saveRoot(page,menuId);
        Body body = BodyFactory.createNewBody();
        bodyService.save(body,page.getPageId());
    }
}
