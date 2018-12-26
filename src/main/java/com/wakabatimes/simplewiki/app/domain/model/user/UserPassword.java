package com.wakabatimes.simplewiki.app.domain.model.user;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Pattern;

/**
 * value object
 */
@Slf4j
@Value
public class UserPassword {

    String value;

    public UserPassword(String value) {
        this.value = value;
    }

    public UserPassword(String value, PasswordEncoder encoder) {
        validatePassword(value);
        this.value = encoder.encode(value);
    }

    private void validatePassword(String value) {
        String inputPattern = "^[a-zA-Z0-9 -/:-@\\[-\\`\\{-\\~]+$";


        if (!Pattern.matches(inputPattern, value)) {
            throw new RuntimeException("パスワードの入力が正しくありません。半角英数で入力してください。");
        }

        Integer count = value.length();
        if(count < 8 || count > 255) {
            throw new RuntimeException("パスワードの入力が正しくありません。 8～255字内で入力してください。n");
        }
    }

}
