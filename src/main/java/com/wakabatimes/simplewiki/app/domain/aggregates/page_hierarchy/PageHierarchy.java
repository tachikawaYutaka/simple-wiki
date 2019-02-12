package com.wakabatimes.simplewiki.app.domain.aggregates.page_hierarchy;

import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PagePath;
import lombok.Getter;
import lombok.NonNull;

public class PageHierarchy {
    @Getter
    @NonNull
    Page page;
    @Getter
    @NonNull
    PagePath pagePath;
    @Getter
    PageHierarchies pageHierarchies;

    public PageHierarchy(Page page, PagePath pagePath, PageHierarchies pageHierarchies) {
        this.page = page;
        this.pagePath = pagePath;
        this.pageHierarchies = pageHierarchies;
    }
}
