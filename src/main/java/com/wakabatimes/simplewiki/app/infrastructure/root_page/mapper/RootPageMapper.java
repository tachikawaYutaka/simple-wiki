package com.wakabatimes.simplewiki.app.infrastructure.root_page.mapper;

import com.wakabatimes.simplewiki.app.infrastructure.root_page.dto.RootPageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RootPageMapper {
    @Select("SELECT \n" +
            "m.id AS menu_id ,\n" +
            "m.name AS menu_name,\n" +
            "m.view_limit AS menu_view_limit,\n" +
            "p.id AS pageId,\n" +
            "p.name AS page_name,\n" +
            "p.type AS page_type\n" +
            "FROM page p\n" +
            "LEFT JOIN relate_page_to_menu rpm ON p.id = rpm.page_id\n" +
            "LEFT JOIN menu m ON m.id = rpm.menu_id\n" +
            "WHERE p.id = #{pageId};")
    RootPageDto getByPageId(RootPageDto input);

    @Select("SELECT \n" +
            "m.id AS menu_id ,\n" +
            "m.name AS menu_name,\n" +
            "m.view_limit AS menu_view_limit,\n" +
            "m.sort_number AS menu_sort_number,\n" +
            "p.id AS pageId,\n" +
            "p.name AS page_name,\n" +
            "p.type AS page_type,\n" +
            "p.sort_number AS page_sort_number\n" +
            "FROM page p\n" +
            "LEFT JOIN relate_page_to_menu rpm ON p.id = rpm.page_id\n" +
            "LEFT JOIN menu m ON m.id = rpm.menu_id\n" +
            "WHERE m.id = #{menuId}" +
            "   AND p.name = #{pageName};")
    RootPageDto getByRootPageByName(RootPageDto input);
}
