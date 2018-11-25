package com.wakabatimes.simplewiki.app.domain.model.page;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

/**
 * value object
 */
@Slf4j
@Value
public class PageName {
    String value;

    public PageName(String value) {
        validateUserName(value);
        this.value = value;
    }

    private void validateUserName(String value) {
        Integer count = value.length();
        if(count < 8 || count > 255) {
            throw new RuntimeException("Menu name is incorrect. 8 or more characters 255 characters within");
        }
    }
}
