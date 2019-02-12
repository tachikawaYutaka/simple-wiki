package com.wakabatimes.simplewiki.app.infrastructure.page.mapper;

import com.wakabatimes.simplewiki.app.infrastructure.page.dto.RelatePageToMenuDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RelatePageTpMenuMapper {
    @Insert("INSERT \n" +
            "INTO relate_page_to_menu(page_id, menu_id) \n" +
            "VALUES (#{pageId}, #{menuId})\n")
    void save(RelatePageToMenuDto pageToMenuDto);
}
