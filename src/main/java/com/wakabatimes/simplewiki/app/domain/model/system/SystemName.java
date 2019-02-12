package com.wakabatimes.simplewiki.app.domain.model.system;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

/**
 * value object
 */
@Slf4j
@Value
public class SystemName {
    String value;

    public SystemName(String value) {
        validateUserName(value);
        this.value = value;
    }

    private void validateUserName(String value) {
        String inputPattern = "^[a-zA-Z0-9 -/:-@\\[-\\`\\{-\\~]+$";

        if (!Pattern.matches(inputPattern, value)) {
            throw new RuntimeException("システム名称の入力が正しくありません。半角英数で入力してください。");
        }

        Integer count = value.length();
        if(count < 3 || count > 20) {
            throw new RuntimeException("システム名称の入力が正しくありません。3～20字内で入力してください。");
        }
    }
}
