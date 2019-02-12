package com.wakabatimes.simplewiki.app.infrastructure.original_html;

import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtml;
import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtmlBody;
import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtmlId;
import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtmlRepository;
import com.wakabatimes.simplewiki.app.infrastructure.original_html.dto.OriginalHtmlDto;
import com.wakabatimes.simplewiki.app.infrastructure.original_html.mapper.OriginalHtmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OriginalHtmlRepositoryImpl implements OriginalHtmlRepository{
    @Autowired
    private OriginalHtmlMapper originalHtmlMapper;

    @Override
    public void save(OriginalHtml originalHtml) {
        OriginalHtmlDto input = new OriginalHtmlDto(originalHtml);
        Integer count = originalHtmlMapper.count();
        if(count == 0){
            originalHtmlMapper.save(input);
        } else {
            throw new RuntimeException("既にデータが存在します。");
        }
    }

    @Override
    public void update(OriginalHtml originalHtml) {
        OriginalHtmlDto input = new OriginalHtmlDto(originalHtml);
        originalHtmlMapper.update(input);
    }

    @Override
    public void delete() {
        Integer count = originalHtmlMapper.count();
        if(count > 0){
            originalHtmlMapper.delete();
        } else {
            throw new RuntimeException("存在しません。");
        }
    }

    @Override
    public boolean exist() {
        Integer count = originalHtmlMapper.count();
        return count > 0;
    }

    @Override
    public OriginalHtml get() {
        OriginalHtmlDto result = originalHtmlMapper.get();
        OriginalHtmlId originalHtmlId = new OriginalHtmlId(result.getId());
        OriginalHtmlBody originalHtmlBody = new OriginalHtmlBody(result.getBody());
        return new OriginalHtml(originalHtmlId,originalHtmlBody);
    }
}
