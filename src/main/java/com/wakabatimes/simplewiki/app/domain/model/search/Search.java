package com.wakabatimes.simplewiki.app.domain.model.search;

import lombok.Getter;
import lombok.NonNull;

/**
 * root entity
 */
public class Search {
    @Getter
    @NonNull
    SearchInput searchInput;

    public Search(SearchInput searchInput){
        this.searchInput = searchInput;
    }
}
