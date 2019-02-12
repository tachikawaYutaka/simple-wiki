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

    @Select("select * from menu where view_limit = #{viewLimit}")
    List<MenuDto> listByMenuLimit(MenuDto input);

    @Select("select * from menu where id = #{id}")
    MenuDto getById(MenuDto input);

    @Select("SELECT * FROM menu \n" +
            "WHERE sort_number = 1\n" +
            "limit 1")
    MenuDto getHomeMenu();

    @Update("UPDATE menu \n" +
            "SET\n" +
            "  sort_number = #{sortNumber}\n" +
            "  , updated = now()\n" +
            "WHERE\n" +
            "  id = #{id}")
    void updateSort(MenuDto menuFirst);
}
