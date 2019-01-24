package com.wakabatimes.simplewiki.app.application.page_with_body;

import com.wakabatimes.simplewiki.app.domain.aggregates.page_with_body.PageWithBodies;
import com.wakabatimes.simplewiki.app.domain.aggregates.page_with_body.PageWithBodyRepository;
import com.wakabatimes.simplewiki.app.domain.model.search.Search;
import com.wakabatimes.simplewiki.app.domain.service.page_with_body.PageWithBodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageWithBodyServiceImpl implements PageWithBodyService{
    @Autowired
    private PageWithBodyRepository pageWithBodyRepository;

    @Override
    public PageWithBodies search(Search search) {
        return pageWithBodyRepository.search(search);
    }
}
