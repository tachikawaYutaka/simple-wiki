package com.wakabatimes.simplewiki.app.infrastructure.body.mapper;

import com.wakabatimes.simplewiki.app.domain.model.body.Bodies;
import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyId;
import com.wakabatimes.simplewiki.app.domain.model.page.PageId;
import com.wakabatimes.simplewiki.app.infrastructure.body.dto.BodyDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BodyMapper {
    @Update("UPDATE body b \n" +
            "LEFT JOIN relate_body_to_page rbtp on b.id = rbtp.body_id\n" +
            "SET b.type = 1\n" +
            "WHERE rbtp.page_id = #{pageId}\n" +
            "AND b.type = 0")
    void removeCurrent(String pageId);

    @Insert("INSERT \n" +
            "INTO body(id, content, type) \n" +
            "VALUES (#{id}, #{content}, #{type})\n")
    void save(BodyDto input);

    @Select("SELECT * FROM body b \n" +
            "LEFT JOIN relate_body_to_page rbtp on b.id = rbtp.body_id\n" +
            "WHERE rbtp.page_id = #{pageId}\n" +
            "AND b.type = 0")
    BodyDto getCurrent(String pageId);

    @Select("SELECT * FROM body b \n" +
            "LEFT JOIN relate_body_to_page rbtp on b.id = rbtp.body_id\n" +
            "WHERE rbtp.page_id = #{pageId}")
    List<BodyDto> getArchive(String pageId);

    @Delete("DELETE \n" +
            "FROM body \n" +
            "WHERE id = #{id}\n")
    void delete(BodyDto input);

    @Select("SELECT * FROM body \n" +
            "WHERE id = #{id}\n")
    BodyDto get(BodyDto input);
}
