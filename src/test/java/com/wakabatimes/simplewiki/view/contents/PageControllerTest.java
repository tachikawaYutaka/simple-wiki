package com.wakabatimes.simplewiki.view.contents;

import com.wakabatimes.simplewiki.SimpleWikiApplication;
import com.wakabatimes.simplewiki.WithMockCustomUser;
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
import com.wakabatimes.simplewiki.app.interfaces.page.form.BranchPageSaveForm;
import com.wakabatimes.simplewiki.app.interfaces.page.form.RootPageSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcOperations;
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
public class PageControllerTest {
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
    @WithMockCustomUser
    public void contentPublicPage() throws Exception {
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

        mockMvc.perform(get("/contents/public/" + menu.getMenuName().getValue() + "/" + page.getPageName().getValue()))
                .andExpect(status().isOk())
                .andExpect(view().name("contents/page"));
    }

    @Test
    @WithMockCustomUser
    public void contentPublicPage2() throws Exception {
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

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.BRANCH;
        Page page2 = PageFactory.create(pageName2,pageType2);
        pageService.saveBranch(page2,page.getPageId());

        Body body2 = BodyFactory.createNewBody();
        bodyService.save(body2,page2.getPageId());

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        mockMvc.perform(get("/contents/public/" + menu.getMenuName().getValue() + "/" + page.getPageName().getValue() + "/" + page2.getPageName().getValue()))
                .andExpect(status().isOk())
                .andExpect(view().name("contents/page"));
    }

    @Test
    public void contentPublicPage_noLoginUser() throws Exception {
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

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.BRANCH;
        Page page2 = PageFactory.create(pageName2,pageType2);
        pageService.saveBranch(page2,page.getPageId());

        Body body2 = BodyFactory.createNewBody();
        bodyService.save(body2,page2.getPageId());

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        mockMvc.perform(get("/contents/public/" + menu.getMenuName().getValue() + "/" + page.getPageName().getValue() + "/" + page2.getPageName().getValue()))
                .andExpect(status().isOk())
                .andExpect(view().name("contents/page"));
    }

    @Test
    @WithMockCustomUser
    public void contentPrivatePage() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PRIVATE;
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

        mockMvc.perform(get("/contents/private/" + menu.getMenuName().getValue() + "/" + page.getPageName().getValue()))
                .andExpect(status().isOk())
                .andExpect(view().name("contents/page"));
    }

    @Test
    @WithMockCustomUser
    public void contentPrivatePage2() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PRIVATE;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        Body body = BodyFactory.createNewBody();
        bodyService.save(body,page.getPageId());

        PageName pageName2 = new PageName("hogehoge2");
        PageType pageType2 = PageType.BRANCH;
        Page page2 = PageFactory.create(pageName2,pageType2);
        pageService.saveBranch(page2,page.getPageId());

        Body body2 = BodyFactory.createNewBody();
        bodyService.save(body2,page2.getPageId());

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        mockMvc.perform(get("/contents/private/" + menu.getMenuName().getValue() + "/" + page.getPageName().getValue() + "/" + page2.getPageName().getValue()))
                .andExpect(status().isOk())
                .andExpect(view().name("contents/page"));
    }

    @Test
    @WithMockCustomUser
    public void rootPageSave() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        RootPageSaveForm rootPageSaveForm = new RootPageSaveForm();
        rootPageSaveForm.setMenuId(menu.getMenuId().getValue());
        rootPageSaveForm.setName("hogehoge");

        mockMvc.perform(
                post("/pages/root")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("menuId",rootPageSaveForm.getMenuId())
                        .param("name",rootPageSaveForm.getName()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("success",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void rootPageSave_fail() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        RootPageSaveForm rootPageSaveForm = new RootPageSaveForm();
        rootPageSaveForm.setMenuId("21e83cb6-bbd4-42ba-aa03-635db77897c6");
        rootPageSaveForm.setName("hogehoge");

        mockMvc.perform(
                post("/pages/root")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("menuId",rootPageSaveForm.getMenuId())
                        .param("name",rootPageSaveForm.getName()))
                .andExpect(flash().attribute("error",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void branchPageSave() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        BranchPageSaveForm branchPageSaveForm = new BranchPageSaveForm();
        branchPageSaveForm.setMenuId(menu.getMenuId().getValue());
        branchPageSaveForm.setParentId(page.getPageId().getValue());
        branchPageSaveForm.setName("hogehoge");

        mockMvc.perform(
                post("/pages/branch")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("menuId",branchPageSaveForm.getMenuId())
                        .param("parentId",branchPageSaveForm.getParentId())
                        .param("name",branchPageSaveForm.getName()))
                .andExpect(flash().attribute("success",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void branchPageSave_fail() throws Exception {
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

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        BranchPageSaveForm branchPageSaveForm = new BranchPageSaveForm();
        branchPageSaveForm.setMenuId(menu.getMenuId().getValue());
        branchPageSaveForm.setParentId(page.getPageId().getValue());
        branchPageSaveForm.setName("hogehoge2");

        mockMvc.perform(
                post("/pages/branch")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("menuId",branchPageSaveForm.getMenuId())
                        .param("parentId",branchPageSaveForm.getParentId())
                        .param("name",branchPageSaveForm.getName()))
                .andExpect(flash().attribute("error",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void pageDelete() throws Exception {
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

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        mockMvc.perform(
                post("/pages/" + menu.getMenuId().getValue() + "/" + page2.getPageId().getValue() + "/delete")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(flash().attribute("success",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void pageDelete_fail() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        mockMvc.perform(
                post("/pages/" + menu.getMenuId().getValue() + "/" + page.getPageId().getValue() + "/delete")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(flash().attribute("error",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void pageNew() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        mockMvc.perform(
                post("/pages/" + menu.getMenuId().getValue() + "/" + page.getPageId().getValue() + "/new")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(flash().attribute("success",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void pageNew2() throws Exception {
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

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        mockMvc.perform(
                post("/pages/" + menu.getMenuId().getValue() + "/" + page2.getPageId().getValue() + "/new")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(flash().attribute("success",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void pageNew_fail() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        PageName pageName = new PageName("hogehoge");
        PageType pageType = PageType.ROOT;
        Page page = PageFactory.create(pageName,pageType);
        pageService.saveRoot(page,menu.getMenuId());

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        mockMvc.perform(
                post("/pages/" + page.getPageId().getValue() + "/" + menu.getMenuId().getValue() +  "/new")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(flash().attribute("error",true))
                .andExpect(status().is3xxRedirection());
    }
}
