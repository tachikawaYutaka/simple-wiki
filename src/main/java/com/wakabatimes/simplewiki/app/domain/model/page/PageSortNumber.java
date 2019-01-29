package com.wakabatimes.simplewiki.app.domain.model.page;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

/**
 * value object
 */
@Slf4j
@Value
public class PageSortNumber {
    Integer value;
    public PageSortNumber(Integer value){
        this.value = value;
    }
}
