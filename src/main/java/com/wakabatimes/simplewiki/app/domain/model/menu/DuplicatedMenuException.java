package com.wakabatimes.simplewiki.app.domain.model.menu;

public class DuplicatedMenuException extends RuntimeException  {
    public DuplicatedMenuException() {
        super("Duplicate exists");
    }

    public DuplicatedMenuException(String s) {
        super(String.format("Duplicate exists [%s]", s));
    }
}
