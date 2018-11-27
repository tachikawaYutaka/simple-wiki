package com.wakabatimes.simplewiki.app.infrastructure.body.mapper;

import com.wakabatimes.simplewiki.app.infrastructure.body.dto.RelateBodyToPageDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RelateBodyToPageMapper {
    @Insert("INSERT \n" +
            "INTO relate_body_to_page(body_id, page_id) \n" +
            "VALUES (#{bodyId}, #{pageId})\n")
    void save(RelateBodyToPageDto input2);
}
