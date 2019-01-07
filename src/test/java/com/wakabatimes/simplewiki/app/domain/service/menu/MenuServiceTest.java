package com.wakabatimes.simplewiki.app.domain.service.menu;

import com.wakabatimes.simplewiki.SimpleWikiApplication;
import com.wakabatimes.simplewiki.app.domain.model.menu.*;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageFactory;
import com.wakabatimes.simplewiki.app.domain.model.page.PageName;
import com.wakabatimes.simplewiki.app.domain.model.page.PageType;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SimpleWikiApplication.class)
public class MenuServiceTest {
    @Autowired
    private MenuService menuService;

    @Autowired
    private JdbcOperations jdbcOperations; // 各テスト前処理用

    @Before
    public void eachBefore() {
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcOperations.execute("DELETE FROM menu");
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=1");
    }

    @After
    public void eachAfter() {
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcOperations.execute("DELETE FROM menu");
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=1");
    }

    @Test
    public void save(){
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);
    }

    @Test(expected = RuntimeException.class)
    public void save_fail(){
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        MenuName menuName2 = new MenuName("hogehoge");
        MenuLimit menuLimit2 = MenuLimit.PUBLIC;
        Menu menu2 = MenuFactory.create(menuName2,menuLimit2);
        menuService.save(menu2);
    }

    @Test
    public void update(){
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        MenuName newName = new MenuName("hogehoge2");
        MenuLimit newLimit = MenuLimit.PRIVATE;
        Menu newMenu = new Menu(menu.getMenuId(),newName,newLimit);
        menuService.update(newMenu);
    }

    @Test(expected = RuntimeException.class)
    public void update_fail(){
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        MenuName menuName2 = new MenuName("hogehoge2");
        MenuLimit menuLimit2 = MenuLimit.PUBLIC;
        Menu menu2 = MenuFactory.create(menuName2,menuLimit2);
        menuService.save(menu2);

        MenuName newName = new MenuName("hogehoge2");
        MenuLimit newLimit = MenuLimit.PRIVATE;
        Menu newMenu = new Menu(menu.getMenuId(),newName,newLimit);
        menuService.update(newMenu);
    }

    @Test
    public void delete(){
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        MenuName menuName2 = new MenuName("hogehoge2");
        MenuLimit menuLimit2 = MenuLimit.PUBLIC;
        Menu menu2 = MenuFactory.create(menuName2,menuLimit2);
        menuService.save(menu2);

        menuService.delete(menu2);
    }

    @Test(expected = RuntimeException.class)
    public void delete_fail(){
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);
        menuService.delete(menu);
    }

    @Test
    public void list(){
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        MenuName menuName2 = new MenuName("hogehoge2");
        MenuLimit menuLimit2 = MenuLimit.PUBLIC;
        Menu menu2 = MenuFactory.create(menuName2,menuLimit2);
        menuService.save(menu2);

        Menus menus = menuService.list();
        assertNotNull(menus);
    }

    @Test
    public void get(){
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        Menu getMenu = menuService.get(menu.getMenuName());
        assertNotNull(getMenu);
    }

    @Test
    public void getById(){
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        Menu getMenu = menuService.getById(menu.getMenuId());
        assertNotNull(getMenu);
    }

    @Test
    public void listByMenuLimit(){
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        Menus getMenu = menuService.listByMenuLimit(menu.getMenuLimit());
        assertNotNull(getMenu);
    }

    @Test
    public void getHomeMenu(){
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        Menu getMenu = menuService.getHomeMenu();
        assertNotNull(getMenu);
    }
}
