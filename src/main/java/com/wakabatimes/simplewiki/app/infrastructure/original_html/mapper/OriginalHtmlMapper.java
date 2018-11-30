package com.wakabatimes.simplewiki.app.infrastructure.original_html.mapper;

import com.wakabatimes.simplewiki.app.infrastructure.original_html.dto.OriginalHtmlDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OriginalHtmlMapper {
    @Select("SELECT COUNT(*) FROM original_html")
    Integer count();
    @Insert("INSERT \n" +
            "INTO original_html(id, body) \n" +
            "VALUES (#{id}, #{body})\n")
    void save(OriginalHtmlDto input);
    @Update("UPDATE original_html \n" +
            "SET\n" +
            "  body = #{body}\n" +
            "WHERE\n" +
            "  id = #{id}\n")
    void update(OriginalHtmlDto input);
    @Delete("DELETE \n" +
            "FROM\n" +
            "  original_html\n")
    void delete();
    @Select("SELECT * from original_html")
    OriginalHtmlDto get();
}
