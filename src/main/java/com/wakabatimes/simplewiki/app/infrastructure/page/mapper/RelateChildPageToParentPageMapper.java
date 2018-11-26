package com.wakabatimes.simplewiki.app.infrastructure.page.mapper;

import com.wakabatimes.simplewiki.app.infrastructure.page.dto.RelateChildPageToParentPageDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RelateChildPageToParentPageMapper {
    @Insert("INSERT \n" +
            "INTO relate_child_page_to_parent_page(child_page_id, parent_page_id) \n" +
            "VALUES (#{childPageId}, #{parentPageId})\n")
    void save(RelateChildPageToParentPageDto relateChildPageToParentPageDto);
}
