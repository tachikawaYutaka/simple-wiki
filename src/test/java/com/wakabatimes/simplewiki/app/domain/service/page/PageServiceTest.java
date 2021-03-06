package com.wakabatimes.simplewiki.app.domain.service.page;

import com.wakabatimes.simplewiki.SimpleWikiApplication;
import com.wakabatimes.simplewiki.app.domain.model.menu.*;
import com.wakabatimes.simplewiki.app.domain.model.page.*;
import com.wakabatimes.simplewiki.app.domain.service.menu.MenuService;
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


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SimpleWikiApplication.class)
public class PageServiceTest {
    @Autowired
    private PageService pageService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private JdbcOperations jdbcOperations; // 各テスト前処理用

    @Before
    public void eachBefore() {
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcOperations.execute("DELETE FROM menu");
        jdbcOperations.execute("DELETE FROM page");
        jdbcOperations.execute("DELETE FROM relate_page_to_menu");
        jdbcOperations.execute("DELETE FROM relate_child_page_to_parent_page");
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=1");
    }

    @After
    public void eachAfter() {
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcOperations.execute("DELETE FROM menu");
        jdbcOperations.execute("DELETE FROM page");
        jdbcOperations.execute("DELETE FROM relate_page_to_menu");
        jdbcOperations.execute("DELETE FROM relate_child_page_to_parent_page");
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=1");
    }

    @Test
    public void saveRoot(){
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());
    }

    @Test(expected = RuntimeException.class)
    public void saveRoot_fail(){
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        PageName pageName2 = new PageName("hogehoge");
        PageType pageType2 = PageType.ROOT;
        Page page2 = PageFactory.create(pageName2,pageType2);
        pageService.saveRoot(page2,menu.getMenuId());
    }

    @Test
    public void saveBranch(){
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.BRANCH;
        Page page2 = PageFactory.create(pageName2,pageType2);
        pageService.saveBranch(page2,page.getPageId());
    }

    @Test(expected = RuntimeException.class)
    public void saveBranch_fail(){
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        PageName pageName2 = new PageName("hogehoge");
        PageType pageType2 = PageType.BRANCH;
        Page page2 = PageFactory.create(pageName2,pageType2);
        pageService.saveBranch(page2,page.getPageId());

        PageName pageName3 = new PageName("hogehoge");
        PageType pageType3 = PageType.BRANCH;
        Page page3 = PageFactory.create(pageName3,pageType3);
        pageService.saveBranch(page3,page.getPageId());
    }

    @Test
    public void updateRoot(){
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        PageName newName = new PageName("hogehoge2");
        Page newPage = new Page(page.getPageId(),newName,page.getPageType());
        pageService.updateRoot(newPage,menu.getMenuId());
    }

    @Test(expected = RuntimeException.class)
    public void updateRoot_fail(){
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.ROOT;
        Page page2 = PageFactory.create(pageName2,pageType2);
        pageService.saveRoot(page2,menu.getMenuId());

        PageName newName = new PageName("hogehoge2");
        Page newPage = new Page(page.getPageId(),newName,page.getPageType());
        pageService.updateRoot(newPage,menu.getMenuId());
    }

    @Test
    public void updateBranch(){
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.BRANCH;
        Page page2 = PageFactory.create(pageName2,pageType2);
        pageService.saveBranch(page2,page.getPageId());

        PageName newName = new PageName("hogehoge3");
        Page newPage = new Page(page2.getPageId(),newName,page2.getPageType());
        pageService.updateBranch(newPage,page.getPageId());
    }

    @Test(expected = RuntimeException.class)
    public void updateBranch_fail(){
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.BRANCH;
        Page page2 = PageFactory.create(pageName2,pageType2);
        pageService.saveBranch(page2,page.getPageId());

        PageName pageName3 = new PageName("hogehoge3");
        PageType pageType3 = PageType.BRANCH;
        Page page3 = PageFactory.create(pageName3,pageType3);
        pageService.saveBranch(page3,page.getPageId());

        PageName newName = new PageName("hogehoge3");
        Page newPage = new Page(page2.getPageId(),newName,page2.getPageType());
        pageService.updateBranch(newPage,page.getPageId());
    }

    @Test
    public void delete(){
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.ROOT;
        Page page2 = PageFactory.create(pageName2,pageType2);
        pageService.saveRoot(page2,menu.getMenuId());

        pageService.delete(page2,menu.getMenuId());
    }

    @Test(expected = RuntimeException.class)
    public void delete_fail(){
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());
        pageService.delete(page,menu.getMenuId());
    }

    @Test
    public void listRoot(){
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.ROOT;
        Page page2 = PageFactory.create(pageName2,pageType2);
        pageService.saveRoot(page2,menu.getMenuId());

        Pages pages = pageService.listRoot(menu.getMenuId());
        assertNotNull(pages);
    }

    @Test
    public void listBranch(){
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.BRANCH;
        Page page2 = PageFactory.create(pageName2,pageType2);
        pageService.saveBranch(page2,page.getPageId());

        PageName pageName3 = new PageName("hogehoge3");
        PageType pageType3 = PageType.BRANCH;
        Page page3 = PageFactory.create(pageName3,pageType3);
        pageService.saveBranch(page3,page.getPageId());

        Pages pages = pageService.listBranch(page.getPageId());
        assertNotNull(pages);
    }

    @Test
    public void get(){
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        Page getPage = pageService.get(page.getPageId());
        assertNotNull(getPage);
    }

    @Test
    public void getPageByParentAndChildName(){
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.BRANCH;
        Page page2 = PageFactory.create(pageName2,pageType2);
        pageService.saveBranch(page2,page.getPageId());

        Page getPage = pageService.getPageByParentAndChildName(page.getPageId(),pageName2);
        assertNotNull(getPage);
    }

    @Test
    public void getHomePage(){
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        Page getPage = pageService.getHomePage(menu.getMenuId());
        assertNotNull(getPage);
    }

    @Test
    public void getParent(){
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.BRANCH;
        Page page2 = PageFactory.create(pageName2,pageType2);
        pageService.saveBranch(page2,page.getPageId());

        Page getPage = pageService.getParent(page2.getPageId());
        assertNotNull(getPage);
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
