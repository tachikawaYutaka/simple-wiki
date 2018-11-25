package com.wakabatimes.simplewiki.app.infrastructure.menu.mapper;

import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.infrastructure.menu.dto.MenuDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuMapper {
    @Select("select * from menu;")
    List<MenuDto> list();

    @Insert("INSERT \n" +
            "INTO menu(id, name, view_limit) \n" +
            "VALUES (#{id}, #{name}, #{viewLimit})")
    void save(MenuDto input);

    @Update("UPDATE menu \n" +
            "SET\n" +
            "  name = #{name}\n" +
            "  , view_limit = #{view_limit}\n" +
            "WHERE\n" +
            "  id = #{id}")
    void update(MenuDto input);

    @Delete("DELETE \n" +
            "FROM\n" +
            "  menu \n" +
            "WHERE\n" +
            "  id = #{id}")
    void delete(MenuDto input);

    @Select("select * from menu where name = #{name}")
    Menu getByMenuName(MenuDto input);
}
