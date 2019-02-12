package com.wakabatimes.simplewiki.app.domain.service.branch_page;

import com.wakabatimes.simplewiki.app.domain.aggregates.branch_page.BranchPage;

public interface BranchPageService {
    /**
     * 子ページの保存
     * @param branchPage
     */
    void save(BranchPage branchPage);
}
