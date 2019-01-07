package com.wakabatimes.simplewiki.view.contents;

import com.wakabatimes.simplewiki.SimpleWikiApplication;
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
import com.wakabatimes.simplewiki.app.domain.service.body.BodyService;
import com.wakabatimes.simplewiki.app.domain.service.menu.MenuService;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import com.wakabatimes.simplewiki.app.domain.service.system.SystemService;
import com.wakabatimes.simplewiki.app.domain.service.user.UserService;
import com.wakabatimes.simplewiki.app.interfaces.body.form.BodySaveForm;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SimpleWikiApplication.class)
public class EditControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PageService pageService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private BodyService bodyService;

    @Autowired
    private UserService userService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private JdbcOperations jdbcOperations; // 各テスト前処理用

    @Before
    public void eachBefore() {
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcOperations.execute("DELETE FROM wiki_user");
        jdbcOperations.execute("DELETE FROM menu");
        jdbcOperations.execute("DELETE FROM page");
        jdbcOperations.execute("DELETE FROM system");
        jdbcOperations.execute("DELETE FROM relate_page_to_menu");
        jdbcOperations.execute("DELETE FROM relate_child_page_to_parent_page");
        jdbcOperations.execute("DELETE FROM body");
        jdbcOperations.execute("DELETE FROM relate_body_to_page");
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=1");
        jdbcOperations.execute("INSERT \n" +
        "INTO wiki_user(id, name, password, role, created, updated) \n" +
        "VALUES ( \n" +
        "  '443ee7b9-76f5-43bb-a2c1-225d0fecfea6'\n" +
        "  , 'testUser'\n" +
        "  , '$2a$10$OARCpRLqglO6nZzRHVVj1e5pbxPNKpSnXfmT11Sx5i4Nzt4Sj7YUi'\n" +
        "  , 0\n" +
        "  , now()\n" +
        "  , now()\n" +
        ") \n");

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @After
    public void eachAfter() {
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcOperations.execute("DELETE FROM wiki_user");
        jdbcOperations.execute("DELETE FROM menu");
        jdbcOperations.execute("DELETE FROM page");
        jdbcOperations.execute("DELETE FROM system");
        jdbcOperations.execute("DELETE FROM relate_page_to_menu");
        jdbcOperations.execute("DELETE FROM relate_child_page_to_parent_page");
        jdbcOperations.execute("DELETE FROM body");
        jdbcOperations.execute("DELETE FROM relate_body_to_page");
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=1");
    }

    @Test
    @WithMockUser(username="testUser",roles={"ADMIN","EDITOR","VIEWER"})
    public void edit() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        Body body = BodyFactory.createNewBody();
        bodyService.save(body,page.getPageId());

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        mockMvc.perform(get("/contents/edit/" + menu.getMenuId().getValue() + "/" + page.getPageId().getValue()))
                .andExpect(status().isOk())
                .andExpect(view().name("contents/edit"));
    }

    @Test
    @WithMockUser(username="testUser",roles={"ADMIN","EDITOR","VIEWER"})
    public void editSave() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        Body body = BodyFactory.createNewBody();
        bodyService.save(body,page.getPageId());

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        BodySaveForm bodySaveForm = new BodySaveForm();
        bodySaveForm.setPageName("hogehoge2");
        bodySaveForm.setContent("hogehoge2");
        bodySaveForm.setHtml("hogehoge2");

        mockMvc.perform(
                post("/contents/edit/" + menu.getMenuId().getValue() + "/" + page.getPageId().getValue())
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("name",bodySaveForm.getPageName())
                .param("content",bodySaveForm.getContent())
                .param("html",bodySaveForm.getHtml()))
            .andExpect(model().hasNoErrors())
            .andExpect(status().is3xxRedirection());

        log.debug(bodyService.getCurrent(page.getPageId()).getBodyContent().getValue());
    }

    @Test
    @WithUserDetails("testUser")
    public void preview(){

    }

    @Test
    @WithUserDetails("testUser")
    public void restore(){

    }

    @Test
    @WithUserDetails("testUser")
    public void delete(){

    }
}
