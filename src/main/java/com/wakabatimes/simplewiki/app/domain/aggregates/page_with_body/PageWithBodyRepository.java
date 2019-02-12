package com.wakabatimes.simplewiki.app.domain.aggregates.page_with_body;

import com.wakabatimes.simplewiki.app.domain.model.search.Search;

public interface PageWithBodyRepository {

    PageWithBodies search(Search search);

}
