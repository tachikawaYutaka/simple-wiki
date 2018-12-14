package com.wakabatimes.simplewiki.app.infrastructure.menu.mapper;

import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.infrastructure.menu.dto.MenuDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuMapper {
    @Select("select * from menu ORDER BY sort_number;")
    List<MenuDto> list();

    @Insert("INSERT \n" +
            "INTO menu(id, name, view_limit, sort_number,created,updated) \n" +
            "VALUES (#{id}, #{name}, #{viewLimit}, #{sortNumber},now(),now())")
    void save(MenuDto input);

    @Update("UPDATE menu \n" +
            "SET\n" +
            "  name = #{name}\n" +
            "  , view_limit = #{viewLimit}\n" +
            "  , updated = now()\n" +
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
    MenuDto getByMenuName(MenuDto input);

    @Select("select * from menu where view_limit = #{limit}")
    List<MenuDto> listByMenuLimit(MenuDto input);
}
