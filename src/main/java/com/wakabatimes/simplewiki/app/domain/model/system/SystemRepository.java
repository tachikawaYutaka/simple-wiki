package com.wakabatimes.simplewiki.app.domain.model.system;

public interface SystemRepository {
    void save(System system);

    void update(System system);

    boolean exist();

    System get();
}
