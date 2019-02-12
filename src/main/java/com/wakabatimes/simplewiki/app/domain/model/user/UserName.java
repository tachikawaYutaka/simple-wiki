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
            throw new RuntimeException("ユーザー名の入力が正しくありません。半角英数で入力してください。");
        }

        Integer count = value.length();
        if(count < 8 || count > 255) {
            throw new RuntimeException("ユーザー名の入力が正しくありません。8～255字内で入力してください。");
        }
    }
}
