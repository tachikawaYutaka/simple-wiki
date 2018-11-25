package com.wakabatimes.simplewiki.app.infrastructure.page.mapper;

import com.wakabatimes.simplewiki.app.infrastructure.page.dto.PageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PageMapper {
    List<PageDto> listByMenuId(String value);

    void save(PageDto input);

    List<PageDto> listByParentPageId(String value);

    void update(PageDto input);

    void delete(PageDto input);

    PageDto getByPageId(PageDto pageDto);
}
