package com.wakabatimes.simplewiki.app.infrastructure.user.mapper;

import com.wakabatimes.simplewiki.app.infrastructure.user.dto.UserDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from wiki_user where name = #{name}")
    UserDto getUserByUserName(UserDto userDto);

    @Insert("INSERT INTO wiki_user (id,name,password,role,created,updated) VALUES (#{id},#{name},#{password},#{role},now(),now())")
    void save(UserDto input);

    @Select("SELECT * from wiki_user")
    List<UserDto> list();

    @Update("UPDATE wiki_user SET password=#{password},updated = now() where id = #{id}")
    void updateUserPassword(UserDto userDto);

    @Delete("DELETE from wiki_user where id = #{id}")
    void delete(UserDto userDto);

    @Select("SELECT COUNT(*) from wiki_user where name = #{name}")
    Long countUserByIdAndName(UserDto userDto);

    @Update("UPDATE wiki_user SET name=#{name}, updated = now() where id = #{id}")
    void updateUserName(UserDto userDto);

    @Select("select * from wiki_user where id = #{id}")
    UserDto getUserById(UserDto input);
}
