package com.wakabatimes.simplewiki.app.domain.model.search;

import org.junit.Test;

public class SearchInputTest {
    @Test
    public void createInstance_success() {
        SearchInput searchInput = new SearchInput("hogehoge");
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_fail() {
        SearchInput searchInput = new SearchInput("");
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_fail2() {
        SearchInput searchInput = new SearchInput("a&b");
    }
}
