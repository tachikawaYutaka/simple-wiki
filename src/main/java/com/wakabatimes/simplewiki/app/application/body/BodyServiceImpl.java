package com.wakabatimes.simplewiki.app.application.body;

import com.wakabatimes.simplewiki.app.domain.model.body.Bodies;
import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyId;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyRepository;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;
import com.wakabatimes.simplewiki.app.domain.service.body.BodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BodyServiceImpl implements BodyService{
    @Autowired
    BodyRepository bodyRepository;

    @Override
    public void save(Body body, PageId pageId) {
        bodyRepository.save(body,pageId);
    }

    @Override
    public Body getCurrent(PageId pageId) {
        return bodyRepository.getCurrent(pageId);
    }

    @Override
    public Bodies getArchive(PageId pageId) {
        return bodyRepository.getArchive(pageId);
    }

    @Override
    public Body get(BodyId bodyId) {
        return bodyRepository.get(bodyId);
    }

    @Override
    public void delete(Body body) {
        bodyRepository.delete(body);
    }
}
