package com.wakabatimes.simplewiki.app.infrastructure.page.mapper;

import com.wakabatimes.simplewiki.app.infrastructure.page.dto.RelatePageToMenuDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RelatePageTpMenuMapper {
    void save(RelatePageToMenuDto pageToMenuDto);
}
