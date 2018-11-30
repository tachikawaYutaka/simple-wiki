package com.wakabatimes.simplewiki.app.infrastructure.system.mapper;

import com.wakabatimes.simplewiki.app.infrastructure.system.dto.SystemDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SystemMapper {
    @Select("SELECT COUNT(*) FROM system")
    Integer count();
    @Insert("INSERT \n" +
            "INTO system(id, name) \n" +
            "VALUES (#{id}, #{name})\n")
    void save(SystemDto input);
    @Update("UPDATE system \n" +
            "SET name = #{name}\n" +
            "WHERE\n" +
            "  id = #{id}\n")
    void update(SystemDto input);
    @Select("SELECT * from system")
    SystemDto get();
}
