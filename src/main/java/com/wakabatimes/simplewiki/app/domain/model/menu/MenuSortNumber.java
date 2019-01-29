package com.wakabatimes.simplewiki.app.domain.model.menu;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

/**
 * value object
 */
@Slf4j
@Value
public class MenuSortNumber {
    Integer value;
    public MenuSortNumber(Integer value){
        this.value = value;
    }
}
