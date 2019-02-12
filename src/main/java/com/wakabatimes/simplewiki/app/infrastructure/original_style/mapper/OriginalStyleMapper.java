package com.wakabatimes.simplewiki.app.infrastructure.original_style.mapper;

import com.wakabatimes.simplewiki.app.infrastructure.original_style.dto.OriginalStyleDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OriginalStyleMapper {
    @Select("SELECT COUNT(*) FROM original_style")
    Integer count();

    @Insert("INSERT \n" +
            "INTO original_style(id, body) \n" +
            "VALUES (#{id}, #{body})\n")
    void save(OriginalStyleDto input);

    @Update("UPDATE original_style \n" +
            "SET\n" +
            "  body = #{body}\n" +
            "WHERE\n" +
            "  id = #{id}\n")
    void update(OriginalStyleDto input);

    @Delete("DELETE \n" +
            "FROM\n" +
            "  original_style\n")
    void delete();

    @Select("SELECT * from original_style")
    OriginalStyleDto get();
}
