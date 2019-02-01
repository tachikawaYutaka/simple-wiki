package com.wakabatimes.simplewiki.app.domain.service.menu;

import com.wakabatimes.simplewiki.SimpleWikiApplication;
import com.wakabatimes.simplewiki.app.domain.model.menu.*;
import com.wakabatimes.simplewiki.app.domain.model.page.*;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static sun.nio.cs.Surrogate.is;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SimpleWikiApplication.class)
public class MenuServiceTest {
    @Autowired
    private MenuService menuService;

    @Autowired
    private PageService pageService;

    @Autowired
    private JdbcOperations jdbcOperations; // 各テスト前処理用

    @Before
    public void eachBefore() {
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcOperations.execute("DELETE FROM menu");
        jdbcOperations.execute("DELETE FROM page");
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=1");
    }

    @After
    public void eachAfter() {
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcOperations.execute("DELETE FROM menu");
        jdbcOperations.execute("DELETE FROM page");
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

    @Test
    public void sortMenu(){
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        MenuSortNumber menuSortNumber = new MenuSortNumber(1);
        Menu menu = MenuFactory.createWithSort(menuName,menuLimit,menuSortNumber);
        menuService.save(menu);

        MenuName menuName2 = new MenuName("hogehoge2");
        MenuLimit menuLimit2 = MenuLimit.PUBLIC;
        MenuSortNumber menuSortNumber2 = new MenuSortNumber(2);
        Menu menu2 = MenuFactory.createWithSort(menuName2,menuLimit2,menuSortNumber2);
        menuService.save(menu2);

        menuService.replaceSort(menu.getMenuId(),menu2.getMenuId());

        Menu result = menuService.getById(menu.getMenuId());
        Assert.assertTrue(result.getMenuSortNumber().getValue() == 2);
    }

    @Test
    public void sortPage(){
        MenuName menuName = new MenuName("hogehoge");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        MenuSortNumber menuSortNumber = new MenuSortNumber(1);
        Menu menu = MenuFactory.createWithSort(menuName,menuLimit,menuSortNumber);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        PageSortNumber pageSortNumber = new PageSortNumber(1);
        Page page = PageFactory.createWithSortNumber(pageName,pageType,pageSortNumber);
        pageService.saveRoot(page,menu.getMenuId());

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.ROOT;
        PageSortNumber pageSortNumber2 = new PageSortNumber(2);
        Page page2 = PageFactory.createWithSortNumber(pageName2,pageType2,pageSortNumber2);
        pageService.saveRoot(page2,menu.getMenuId());

        pageService.replaceSort(page.getPageId(),page2.getPageId());
        Page result = pageService.get(page.getPageId());

        Assert.assertTrue(result.getPageSortNumber().getValue() == 2);
    }
}
