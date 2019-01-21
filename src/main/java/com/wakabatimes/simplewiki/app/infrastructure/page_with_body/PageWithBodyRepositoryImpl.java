package com.wakabatimes.simplewiki.app.infrastructure.page_with_body;

import com.wakabatimes.simplewiki.app.domain.aggregates.page_with_body.PageWithBodies;
import com.wakabatimes.simplewiki.app.domain.aggregates.page_with_body.PageWithBody;
import com.wakabatimes.simplewiki.app.domain.aggregates.page_with_body.PageWithBodyRepository;
import com.wakabatimes.simplewiki.app.domain.model.body.*;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;
import com.wakabatimes.simplewiki.app.domain.model.page.PageName;
import com.wakabatimes.simplewiki.app.domain.model.page.PageType;
import com.wakabatimes.simplewiki.app.domain.model.search.Search;
import com.wakabatimes.simplewiki.app.infrastructure.page_with_body.dto.PageWithBodyDto;
import com.wakabatimes.simplewiki.app.infrastructure.page_with_body.mapper.PageWithBodyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PageWithBodyRepositoryImpl implements PageWithBodyRepository{
    @Autowired
    private PageWithBodyMapper pageWithBodyMapper;

    @Override
    public PageWithBodies search(Search search) {
        String input = "%" + search.getSearchInput().getValue() + "%";
        List<PageWithBodyDto> results = pageWithBodyMapper.search(input);

        PageWithBodies pageWithBodies = new PageWithBodies();
        for(PageWithBodyDto result : results){
            PageId pageId = new PageId(result.getPageId());
            PageName pageName = new PageName(result.getPageName());
            PageType pageType = PageType.getById(result.getPageType());
            Page page = new Page(pageId,pageName,pageType);

            BodyId bodyId = new BodyId(result.getBodyId());
            BodyContent bodyContent = new BodyContent(result.getBodyContent());
            BodyHtml bodyHtml = new BodyHtml(result.getBodyHtml());
            BodyType bodytype =  BodyType.getById(result.getBodyType());
            Body body = new Body(bodyId,bodyContent,bodyHtml,bodytype);

            PageWithBody pageWithBody = new PageWithBody(page,body);
            pageWithBodies.add(pageWithBody);
        }
        return pageWithBodies;
    }
}
