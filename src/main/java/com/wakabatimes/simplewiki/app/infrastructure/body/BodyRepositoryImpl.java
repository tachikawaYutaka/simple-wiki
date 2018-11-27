package com.wakabatimes.simplewiki.app.infrastructure.body;

import com.wakabatimes.simplewiki.app.domain.model.body.*;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;
import com.wakabatimes.simplewiki.app.infrastructure.body.dto.BodyDto;
import com.wakabatimes.simplewiki.app.infrastructure.body.dto.RelateBodyToPageDto;
import com.wakabatimes.simplewiki.app.infrastructure.body.mapper.BodyMapper;
import com.wakabatimes.simplewiki.app.infrastructure.body.mapper.RelateBodyToPageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BodyRepositoryImpl implements BodyRepository{
    @Autowired
    BodyMapper bodyMapper;
    @Autowired
    RelateBodyToPageMapper relateBodyToPageMapper;

    @Override
    public void save(Body body, PageId pageId) {
        BodyDto input = new BodyDto(body);
        bodyMapper.removeCurrent(pageId);
        bodyMapper.save(input);

        RelateBodyToPageDto input2 = new RelateBodyToPageDto(body.getBodyId(), pageId);
        relateBodyToPageMapper.save(input2);
    }

    @Override
    public Body getCurrent(PageId pageId) {
        BodyDto result = bodyMapper.getCurrent(pageId.getValue());
        BodyId bodyId = new BodyId(result.getId());
        BodyContent bodyContent = new BodyContent(result.getContent());
        BodyType bodyType = BodyType.getById(result.getType());
        Body body = new Body(bodyId,bodyContent,bodyType);
        return body;
    }

    @Override
    public Bodies getArchive(PageId pageId) {
        List<BodyDto> results = bodyMapper.getArchive(pageId.getValue());
        Bodies bodies = new Bodies();
        for(BodyDto result: results) {
            BodyId bodyId = new BodyId(result.getId());
            BodyContent bodyContent = new BodyContent(result.getContent());
            BodyType bodyType = BodyType.getById(result.getType());
            Body body = new Body(bodyId,bodyContent,bodyType);
            bodies.add(body);
        }
        return bodies;
    }

    @Override
    public void delete(Body body) {
        BodyDto input = new BodyDto(body);
        if(!body.isCurrent(body)){
            bodyMapper.delete(input);
        }
    }

    @Override
    public Body get(BodyId bodyId) {
        BodyDto input = new BodyDto();
        input.setId(bodyId.getValue());
        BodyDto result = bodyMapper.get(input);
        BodyId bodyId1 = new BodyId(result.getId());
        BodyContent bodyContent = new BodyContent(result.getContent());
        BodyType bodyType = BodyType.getById(result.getType());
        Body body = new Body(bodyId1,bodyContent,bodyType);
        return body;
    }
}
