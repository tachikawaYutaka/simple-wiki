package com.wakabatimes.simplewiki.app.infrastructure.original_style;

import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyle;
import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyleBody;
import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyleId;
import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyleRepository;
import com.wakabatimes.simplewiki.app.infrastructure.original_style.dto.OriginalStyleDto;
import com.wakabatimes.simplewiki.app.infrastructure.original_style.mapper.OriginalStyleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OriginalStyleRepositoryImpl implements OriginalStyleRepository{
    @Autowired
    OriginalStyleMapper originalStyleMapper;

    @Override
    public void save(OriginalStyle originalStyle) {
        OriginalStyleDto input = new OriginalStyleDto(originalStyle);
        Integer count = originalStyleMapper.count();
        if(count == 0) {
            originalStyleMapper.save(input);
        }else {
            throw new RuntimeException("既にデータが存在します。");
        }

    }

    @Override
    public void update(OriginalStyle originalStyle) {
        OriginalStyleDto input = new OriginalStyleDto(originalStyle);
        originalStyleMapper.update(input);
    }

    @Override
    public void delete() {
        Integer count = originalStyleMapper.count();
        if(count > 0){
            originalStyleMapper.delete();
        } else {
            throw new RuntimeException("存在しません。");
        }
    }

    @Override
    public boolean exist() {
        Integer count = originalStyleMapper.count();
        if(count > 0){
            return true;
        }
        return false;
    }

    @Override
    public OriginalStyle get() {
        OriginalStyleDto result = originalStyleMapper.get();
        OriginalStyleId originalStyleId = new OriginalStyleId(result.getId());
        OriginalStyleBody originalStyleBody = new OriginalStyleBody(result.getBody());
        OriginalStyle originalStyle = new OriginalStyle(originalStyleId,originalStyleBody);
        return originalStyle;
    }
}
