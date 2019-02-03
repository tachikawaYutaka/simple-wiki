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
    private BodyMapper bodyMapper;
    @Autowired
    private RelateBodyToPageMapper relateBodyToPageMapper;

    @Override
    public void save(Body body, PageId pageId) {
        BodyDto input = new BodyDto(body);
        bodyMapper.removeCurrent(pageId.getValue());
        bodyMapper.save(input);

        RelateBodyToPageDto input2 = new RelateBodyToPageDto(body.getBodyId(), pageId);
        relateBodyToPageMapper.save(input2);
    }

    @Override
    public Body getCurrent(PageId pageId) {
        BodyDto result = bodyMapper.getCurrent(pageId.getValue());
        BodyId bodyId = new BodyId(result.getId());
        BodyContent bodyContent = new BodyContent(result.getContent());
        BodyHtml bodyHtml = new BodyHtml(result.getHtml());
        BodyType bodyType = BodyType.getById(result.getType());
        BodyCreatedDate bodyCreatedDate = new BodyCreatedDate(result.getCreated());
        return new Body(bodyId,bodyContent,bodyHtml, bodyType, bodyCreatedDate);
    }

    @Override
    public Bodies getArchive(PageId pageId) {
        List<BodyDto> results = bodyMapper.getArchive(pageId.getValue());
        Bodies bodies = new Bodies();
        for(BodyDto result: results) {
            BodyId bodyId = new BodyId(result.getId());
            BodyContent bodyContent = new BodyContent(result.getContent());
            BodyHtml bodyHtml = new BodyHtml(result.getHtml());
            BodyType bodyType = BodyType.getById(result.getType());
            BodyCreatedDate bodyCreatedDate = new BodyCreatedDate(result.getCreated());
            Body body = new Body(bodyId,bodyContent,bodyHtml, bodyType, bodyCreatedDate);
            bodies.add(body);
        }
        return bodies;
    }

    @Override
    public void delete(Body body) {
        BodyDto input = new BodyDto(body);
        if(!body.isCurrent(body)){
            bodyMapper.delete(input);
        }else {
            throw new RuntimeException("最新のボディは削除できません。");
        }
    }

    @Override
    public Body get(BodyId bodyId) {
        BodyDto input = new BodyDto();
        input.setId(bodyId.getValue());
        BodyDto result = bodyMapper.get(input);
        if(result != null){
            BodyId bodyId1 = new BodyId(result.getId());
            BodyContent bodyContent = new BodyContent(result.getContent());
            BodyHtml bodyHtml = new BodyHtml(result.getHtml());
            BodyType bodyType = BodyType.getById(result.getType());
            BodyCreatedDate bodyCreatedDate = new BodyCreatedDate(result.getCreated());
            return new Body(bodyId1,bodyContent,bodyHtml, bodyType, bodyCreatedDate);
        }else {
            throw new RuntimeException("指定したボディが存在しません。");
        }
    }
}
