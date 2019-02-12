package com.wakabatimes.simplewiki.app.infrastructure.page_with_body.mapper;

import com.wakabatimes.simplewiki.app.infrastructure.page_with_body.dto.PageWithBodyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PageWithBodyMapper {
    @Select("SELECT \n" +
            "p.id AS pageId,\n" +
            "p.name AS pageName,\n" +
            "p.type AS pageType,\n" +
            "p.sort_number AS pageSortNumber,\n" +
            "b.id AS bodyId,\n" +
            "b.content AS bodyContent,\n" +
            "b.html AS bodyHtml,\n" +
            "b.type AS bodyType \n" +
            "FROM page p\n" +
            "LEFT JOIN relate_body_to_page r ON r.page_id = p.id\n" +
            "LEFT JOIN body b ON b.id = r.body_id\n" +
            "WHERE b.type = 0\n" +
            "AND (p.name LIKE #{input}\n" +
            "OR b.html LIKE #{input});")
    List<PageWithBodyDto> search(String input);
}
