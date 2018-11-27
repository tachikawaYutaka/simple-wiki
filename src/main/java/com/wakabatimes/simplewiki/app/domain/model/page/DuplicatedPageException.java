package com.wakabatimes.simplewiki.app.domain.model.page;

public class DuplicatedPageException extends RuntimeException  {
    public DuplicatedPageException(String s) {
        super(String.format("Duplicate exists [%s]", s));
    }
}
