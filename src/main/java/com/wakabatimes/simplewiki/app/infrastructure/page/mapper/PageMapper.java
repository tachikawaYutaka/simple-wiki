package com.wakabatimes.simplewiki.app.infrastructure.page.mapper;

import com.wakabatimes.simplewiki.app.infrastructure.page.dto.PageDto;
import com.wakabatimes.simplewiki.app.infrastructure.page_with_body.dto.PageWithBodyDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PageMapper {
    @Select("SELECT * FROM page p\n" +
            "LEFT JOIN relate_page_to_menu rpm ON p.id = rpm.page_id\n" +
            "WHERE rpm.menu_id = #{value}" +
            "ORDER BY p.sort_number;")
    List<PageDto> listByMenuId(String value);

    @Insert("INSERT \n" +
            "INTO page(id, name, type, sort_number,created,updated) \n" +
            "VALUES (#{id}, #{name}, #{type}, #{sortNumber}, now(),now())")
    void save(PageDto input);

    @Select("SELECT * FROM page p\n" +
            "LEFT JOIN relate_child_page_to_parent_page rcptpp ON p.id = rcptpp.child_page_id\n" +
            "WHERE rcptpp.parent_page_id = #{value}" +
            "ORDER BY p.sort_number;")
    List<PageDto> listByParentPageId(String value);

    @Update("UPDATE page \n" +
            "SET\n" +
            "   name = #{name}\n" +
            "  , type = #{type}\n" +
            "  , updated = now()\n" +
            "WHERE\n" +
            "  id = #{id}\n")
    void update(PageDto input);

    @Delete("DELETE \n" +
            "FROM\n" +
            "  page \n" +
            "WHERE\n" +
            "  id = #{id}\n")
    void delete(PageDto input);

    @Select("SELECT * FROM page \n" +
            "WHERE id = #{id}")
    PageDto getByPageId(PageDto pageDto);

    @Select("SELECT * FROM page p\n" +
            "LEFT JOIN relate_child_page_to_parent_page rcptpp ON p.id = rcptpp.child_page_id\n" +
            "WHERE rcptpp.parent_page_id = #{id}" +
            "AND p.name = #{name}")
    PageDto getPageByParentAndChildName(PageDto input);

    @Select("SELECT * FROM page p\n" +
            "LEFT JOIN relate_page_to_menu rpm ON p.id = rpm.page_id\n" +
            "WHERE rpm.menu_id = #{value}\n" +
            "AND p.sort_number = 1\n" +
            "LIMIT 1")
    PageDto getHomePage(String value);

    @Select("SELECT pp.* FROM page p\n" +
            "LEFT JOIN relate_child_page_to_parent_page rp\n" +
            "ON p.id = rp.child_page_id \n" +
            "LEFT JOIN page pp\n" +
            "ON pp.id = rp.parent_page_id \n" +
            "WHERE p.id = #{id}")
    PageDto getParent(PageDto input);


    @Update("UPDATE page \n" +
            "SET\n" +
            "   sort_number = #{sortNumber}\n" +
            "  , updated = now()\n" +
            "WHERE\n" +
            "  id = #{id}\n")
    void updateSort(PageDto pageFirst);
}
