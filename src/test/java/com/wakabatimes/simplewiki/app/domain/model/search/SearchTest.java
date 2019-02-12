package com.wakabatimes.simplewiki.app.domain.model.search;

import org.junit.Test;

public class SearchTest {
    @Test
    public void createInstance_success() {
        SearchInput searchInput = new SearchInput("hogehoge");
        Search search = new Search(searchInput);
    }
}
