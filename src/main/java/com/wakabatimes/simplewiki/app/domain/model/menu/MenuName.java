package com.wakabatimes.simplewiki.app.domain.model.menu;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
/**
 * value object
 */
@Slf4j
@Value
public class MenuName {
    String value;

    public MenuName(String value) {
        validateUserName(value);
        this.value = value;
    }

    private void validateUserName(String value) {
        Integer count = value.length();
        if(count < 4 || count > 255) {
            throw new RuntimeException("Menu name is incorrect. 4 or more characters 255 characters within");
        }
    }
}
