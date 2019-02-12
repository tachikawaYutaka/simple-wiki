package com.wakabatimes.simplewiki.app.domain.aggregates.branch_page;

import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;
import lombok.Getter;
import lombok.NonNull;

public class BranchPage {
    @Getter
    @NonNull
    PageId parentId;
    @Getter
    @NonNull
    Page page;

    public BranchPage(PageId pageId, Page page) {
        this.parentId = pageId;
        this.page = page;
    }
}
