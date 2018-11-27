package com.wakabatimes.simplewiki.app.domain.model.menu;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MenusTest {
    @Test
    public void add() {
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);

        MenuName menuName2 = new MenuName("hogehoge2");
        MenuLimit menuLimit2 = MenuLimit.PUBLIC;
        Menu menu2 = MenuFactory.create(menuName2,menuLimit2);

        Menus menus = new Menus();
        menus.add(menu);
        menus.add(menu2);

        assertEquals(2, menus.size().intValue());
    }

    @Test
    public void menus() {
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);

        MenuName menuName2 = new MenuName("hogehoge2");
        MenuLimit menuLimit2 = MenuLimit.PUBLIC;
        Menu menu2 = MenuFactory.create(menuName2,menuLimit2);

        List<Menu> menuList = new ArrayList<>();
        menuList.add(menu);
        menuList.add(menu2);

        Menus menus = new Menus(menuList);

        assertEquals(2, menus.size().intValue());
    }

    @Test
    public void list() {
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);

        MenuName menuName2 = new MenuName("hogehoge2");
        MenuLimit menuLimit2 = MenuLimit.PUBLIC;
        Menu menu2 = MenuFactory.create(menuName2,menuLimit2);

        List<Menu> menuList = new ArrayList<>();
        menuList.add(menu);
        menuList.add(menu2);
        Menus menus = new Menus(menuList);

        assertEquals(menuList, menus.list());
    }

    @Test(expected = RuntimeException.class)
    public void duplicate_add_to_fail_same_username() {
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);

        MenuName menuName2 = new MenuName("hogehoge");
        MenuLimit menuLimit2 = MenuLimit.PUBLIC;
        Menu menu2 = MenuFactory.create(menuName2,menuLimit2);

        Menus menus = new Menus();
        menus.add(menu);
        menus.add(menu2);
    }
}
