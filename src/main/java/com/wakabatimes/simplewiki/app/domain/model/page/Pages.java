package com.wakabatimes.simplewiki.app.domain.model.page;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pages {
    List<Page> pages;

    public Pages () {
        pages = new ArrayList<>();
    }

    public Pages(List<Page> pages) {
        this.pages = new ArrayList<>(pages);
    }

    /**
     * menu add
     * @param page
     * @throws RuntimeException
     */
    public void add(@NonNull Page page) throws RuntimeException {
        if(this.containsName(page)) {
            throw new DuplicatedPageException(page.toString());
        }
        this.pages.add(page);
    }

    /**
     * users count
     * @return Integer
     */
    public Integer size() {
        return this.pages.size();
    }

    /**
     * containName
     * @param compare_page
     * @return Boolean
     */
    public boolean containsName(Page compare_page) {
        for(Page page : this.pages) {
            if (page.getPageName().equals(compare_page.getPageName()) && !page.getPageId().equals(compare_page.getPageId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * get list
     *
     * @return List<User>
     */
    public List<Page> list() {
        return Collections.unmodifiableList(this.pages);
    }
}
