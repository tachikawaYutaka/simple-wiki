package com.wakabatimes.simplewiki.app.domain.model.body;

import com.wakabatimes.simplewiki.app.domain.model.page.PageId;

public interface BodyRepository {

    void save(Body body, PageId pageId);

    Body getCurrent(PageId pageId);

    Bodies getArchive(PageId pageId);

    void delete(Body body);

    Body get(BodyId bodyId);
}
