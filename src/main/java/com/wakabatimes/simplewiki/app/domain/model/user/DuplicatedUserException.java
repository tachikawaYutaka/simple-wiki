package com.wakabatimes.simplewiki.app.domain.model.user;

public class DuplicatedUserException extends RuntimeException  {
    public DuplicatedUserException(String s) {
        super(String.format("Duplicate exists [%s]", s));
    }
}
