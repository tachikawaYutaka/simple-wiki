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
            throw new RuntimeException("Input is incorrect. Expected value [" + inputPattern + "] [value=" + value + "]");
        }

        Integer count = value.length();
        if(count < 8 || count > 255) {
            throw new RuntimeException("Password is incorrect. 8 or more characters 255 characters within");
        }
    }

}
