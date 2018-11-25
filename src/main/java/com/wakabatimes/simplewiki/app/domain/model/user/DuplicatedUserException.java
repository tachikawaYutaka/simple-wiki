package com.wakabatimes.simplewiki.app.domain.model.user;

public class DuplicatedUserException extends RuntimeException  {
    public DuplicatedUserException() {
        super("Duplicate exists");
    }

    public DuplicatedUserException(String s) {
        super(String.format("Duplicate exists [%s]", s));
    }
}
