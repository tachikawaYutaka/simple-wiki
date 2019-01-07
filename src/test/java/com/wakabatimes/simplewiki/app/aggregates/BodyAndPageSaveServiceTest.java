package com.wakabatimes.simplewiki.app.aggregates;

import com.wakabatimes.simplewiki.SimpleWikiApplication;
import com.wakabatimes.simplewiki.app.domain.model.body.Body;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyContent;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyFactory;
import com.wakabatimes.simplewiki.app.domain.model.body.BodyHtml;
import com.wakabatimes.simplewiki.app.domain.model.menu.Menu;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuFactory;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuLimit;
import com.wakabatimes.simplewiki.app.domain.model.menu.MenuName;
import com.wakabatimes.simplewiki.app.domain.model.page.Page;
import com.wakabatimes.simplewiki.app.domain.model.page.PageFactory;
import com.wakabatimes.simplewiki.app.domain.model.page.PageName;
import com.wakabatimes.simplewiki.app.domain.model.page.PageType;
import com.wakabatimes.simplewiki.app.domain.service.body.BodyService;
import com.wakabatimes.simplewiki.app.domain.service.menu.MenuService;
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

import static org.hamcrest.core.Is.is;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SimpleWikiApplication.class)
public class BodyAndPageSaveServiceTest {
    @Autowired
    private PageService pageService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private BodyService bodyService;

    @Autowired
    private RootPageCreateService rootPageCreateService;

    @Autowired
    private BranchPageCreateService branchPageCreateService;

    @Autowired
    private BodyAndPageSaveService bodyAndPageSaveService;

    @Autowired
    private JdbcOperations jdbcOperations; // 各テスト前処理用

    @Before
    public void eachBefore() {
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=0");
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
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        rootPageCreateService.save(page,menu.getMenuId());

        BodyContent bodyContent = new BodyContent("hogehoge");
        BodyHtml bodyHtml = new BodyHtml("hogehoge");
        Body body = BodyFactory.create(bodyContent,bodyHtml);

        bodyAndPageSaveService.save(body,menu.getMenuId(),page);
        Assert.assertThat(bodyService.getCurrent(page.getPageId()).getBodyContent().getValue(),is("hogehoge"));
    }

    @Test
    public void save2(){
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);

        rootPageCreateService.save(page,menu.getMenuId());

        BodyContent bodyContent = new BodyContent("hogehoge");
        BodyHtml bodyHtml = new BodyHtml("hogehoge");
        Body body = BodyFactory.create(bodyContent,bodyHtml);

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.BRANCH;
        Page page2 = PageFactory.create(pageName2,pageType2);
        branchPageCreateService.save(page2,page.getPageId());

        BodyContent bodyContent2 = new BodyContent("hogehoge2");
        BodyHtml bodyHtml2 = new BodyHtml("hogehoge2");
        Body body2 = BodyFactory.create(bodyContent2,bodyHtml2);

        bodyAndPageSaveService.save(body2,menu.getMenuId(),page2);
        Assert.assertThat(bodyService.getCurrent(page2.getPageId()).getBodyContent().getValue(),is("hogehoge2"));
    }


}
