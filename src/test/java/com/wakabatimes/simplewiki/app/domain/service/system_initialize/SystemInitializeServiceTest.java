package com.wakabatimes.simplewiki.app.domain.service.system_initialize;

import com.wakabatimes.simplewiki.SimpleWikiApplication;
import com.wakabatimes.simplewiki.app.application.system_initialize.SystemInitializeServiceImpl;
import com.wakabatimes.simplewiki.app.domain.aggregates.sytem_initialize.SystemInitialize;
import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyFactory;
import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuFactory;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuLimit;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuName;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageFactory;
import com.wakabatimes.simplewiki.app.domain.model.page.PageName;
import com.wakabatimes.simplewiki.app.domain.model.page.PageType;
import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.model.system.SystemFactory;
import com.wakabatimes.simplewiki.app.domain.model.system.SystemName;
import com.wakabatimes.simplewiki.app.domain.model.user.*;
import com.wakabatimes.simplewiki.app.domain.service.menu.MenuService;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import com.wakabatimes.simplewiki.app.domain.service.system.SystemService;
import com.wakabatimes.simplewiki.app.domain.service.user.UserService;
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

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SimpleWikiApplication.class)
public class SystemInitializeServiceTest {
    @Autowired
    private SystemInitializeService systemInitializeService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private PageService pageService;

    @Autowired
    private JdbcOperations jdbcOperations; // 各テスト前処理用

    @Before
    public void eachBefore() {
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcOperations.execute("DELETE FROM wiki_user");
        jdbcOperations.execute("DELETE FROM system");
        jdbcOperations.execute("DELETE FROM menu");
        jdbcOperations.execute("DELETE FROM page");
        jdbcOperations.execute("DELETE FROM relate_page_to_menu");
        jdbcOperations.execute("DELETE FROM relate_child_page_to_parent_page");
        jdbcOperations.execute("DELETE FROM body");
        jdbcOperations.execute("DELETE FROM relate_body_to_page");
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=1");
    }

    @After
    public void eachAfter() {
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcOperations.execute("DELETE FROM wiki_user");
        jdbcOperations.execute("DELETE FROM system");
        jdbcOperations.execute("DELETE FROM menu");
        jdbcOperations.execute("DELETE FROM page");
        jdbcOperations.execute("DELETE FROM relate_page_to_menu");
        jdbcOperations.execute("DELETE FROM relate_child_page_to_parent_page");
        jdbcOperations.execute("DELETE FROM body");
        jdbcOperations.execute("DELETE FROM relate_body_to_page");
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=1");
    }

    @Test
    public void save(){
        //システムの登録
        SystemName systemName = new SystemName("hogehoge");
        System system = SystemFactory.create(systemName);

        //管理者ユーザーの作成
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        User user = UserFactory.create(userName, userPassword, UserRole.ROLE_ADMIN);

        //メニューの作成
        MenuName menuName = new MenuName("ホーム");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);

        //メインページの作成
        PageName pageName = new PageName("メインページ");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        //ページボディの作成
        Body body = BodyFactory.createNewBody();

        SystemInitialize systemInitialize = new SystemInitialize(system,user,menu,page,body);
        systemInitializeService.save(systemInitialize);
        Assert.assertTrue(systemService.exist());
        Assert.assertNotNull(userService.get(userName));
        Assert.assertNotNull(menuService.getById(menu.getMenuId()));
        Assert.assertNotNull(pageService.get(page.getPageId()));
    }

}
