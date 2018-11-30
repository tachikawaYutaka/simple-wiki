package com.wakabatimes.simplewiki.app.domain.model.original_style;

public interface OriginalStyleRepository {
    void save(OriginalStyle originalStyle);

    void update(OriginalStyle originalStyle);

    void delete();

    boolean exist();

    OriginalStyle get();
}
