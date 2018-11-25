package com.wakabatimes.simplewiki.app.domain.model.user;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

/**
 * value object
 */
@Slf4j
@Value
public class UserName {
    String value;

    public UserName(String value) {
        validateUserName(value);
        this.value = value;
    }

    private void validateUserName(String value) {
        String inputPattern = "^[a-zA-Z0-9 -/:-@\\[-\\`\\{-\\~]+$";

        if (!Pattern.matches(inputPattern, value)) {
            throw new RuntimeException("Input is incorrect. Expected value [" + inputPattern + "] [value=" + value + "]");
        }

        Integer count = value.length();
        if(count < 8 || count > 255) {
            throw new RuntimeException("User name is incorrect. 8 or more characters 255 characters within");
        }
    }
}
