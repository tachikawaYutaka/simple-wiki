package com.wakabatimes.simplewiki.app.application.branch_page;

import com.wakabatimes.simplewiki.app.domain.aggregates.branch_page.BranchPage;
import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyFactory;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;
import com.wakabatimes.simplewiki.app.domain.service.body.BodyService;
import com.wakabatimes.simplewiki.app.domain.service.branch_page.BranchPageService;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BranchPageServiceImpl implements BranchPageService{
    @Autowired
    private PageService pageService;

    @Autowired
    private BodyService bodyService;

    @Override
    @Transactional
    public void save(BranchPage branchPage) {
        pageService.saveBranch(branchPage.getPage(),branchPage.getParentId());
        Body body = BodyFactory.createNewBody();
        bodyService.save(body,branchPage.getPage().getPageId());
    }
}
