package com.wakabatimes.simplewiki.view.contents;

import com.wakabatimes.simplewiki.SimpleWikiApplication;
import com.wakabatimes.simplewiki.WithMockCustomUser;
import com.wakabatimes.simplewiki.app.domain.model.menu.*;
import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtml;
import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtmlBody;
import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtmlFactory;
import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyle;
import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyleBody;
import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyleFactory;
import com.wakabatimes.simplewiki.app.domain.model.page.*;
import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.model.system.SystemFactory;
import com.wakabatimes.simplewiki.app.domain.model.system.SystemName;
import com.wakabatimes.simplewiki.app.domain.service.body.BodyService;
import com.wakabatimes.simplewiki.app.domain.service.menu.MenuService;
import com.wakabatimes.simplewiki.app.domain.service.original_html.OriginalHtmlService;
import com.wakabatimes.simplewiki.app.domain.service.original_style.OriginalStyleService;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import com.wakabatimes.simplewiki.app.domain.service.system.SystemService;
import com.wakabatimes.simplewiki.app.domain.service.user.UserService;
import com.wakabatimes.simplewiki.app.interfaces.menu.form.MenuSaveForm;
import com.wakabatimes.simplewiki.app.interfaces.menu.form.MenuSortForm;
import com.wakabatimes.simplewiki.app.interfaces.menu.form.MenuUpdateForm;
import com.wakabatimes.simplewiki.app.interfaces.page.form.PageSortForm;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
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
public class MenuControllerTest{
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
    private OriginalStyleService originalStyleService;

    @Autowired
    private OriginalHtmlService originalHtmlService;

    @Autowired
    private JdbcOperations jdbcOperations; // 各テスト前処理用

    @Before
    public void eachBefore() {
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcOperations.execute("DELETE FROM wiki_user");
        jdbcOperations.execute("DELETE FROM menu");
        jdbcOperations.execute("DELETE FROM page");
        jdbcOperations.execute("DELETE FROM system");
        jdbcOperations.execute("DELETE FROM original_style");
        jdbcOperations.execute("DELETE FROM original_html");
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
        jdbcOperations.execute("DELETE FROM original_style");
        jdbcOperations.execute("DELETE FROM original_html");
        jdbcOperations.execute("DELETE FROM relate_page_to_menu");
        jdbcOperations.execute("DELETE FROM relate_child_page_to_parent_page");
        jdbcOperations.execute("DELETE FROM body");
        jdbcOperations.execute("DELETE FROM relate_body_to_page");
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=1");
    }

    @Test
    @WithMockCustomUser
    public void contentPublicMenu() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        OriginalHtmlBody originalHtmlBody = new OriginalHtmlBody("hogehoge");
        OriginalHtml originalHtml = OriginalHtmlFactory.create(originalHtmlBody);
        originalHtmlService.save(originalHtml);

        OriginalStyleBody originalStyleBody = new OriginalStyleBody("hogehoge");
        OriginalStyle originalStyle = OriginalStyleFactory.create(originalStyleBody);
        originalStyleService.save(originalStyle);

        mockMvc.perform(get("/contents/public/" + menu.getMenuName().getValue()))
                .andExpect(status().isOk())
                .andExpect(view().name("contents/menu"));
    }

    @Test
    @WithMockCustomUser
    public void contentPublicMenu2() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        mockMvc.perform(get("/contents/public/" + menu.getMenuName().getValue()))
                .andExpect(status().isOk())
                .andExpect(view().name("contents/menu"));
    }

    @Test
    public void contentPublicMenu_noLoginUser() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        OriginalHtmlBody originalHtmlBody = new OriginalHtmlBody("hogehoge");
        OriginalHtml originalHtml = OriginalHtmlFactory.create(originalHtmlBody);
        originalHtmlService.save(originalHtml);

        OriginalStyleBody originalStyleBody = new OriginalStyleBody("hogehoge");
        OriginalStyle originalStyle = OriginalStyleFactory.create(originalStyleBody);
        originalStyleService.save(originalStyle);

        mockMvc.perform(get("/contents/public/" + menu.getMenuName().getValue()))
                .andExpect(status().isOk())
                .andExpect(view().name("contents/menu"));
    }

    @Test
    @WithMockCustomUser
    public void contentPrivateMenu() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PRIVATE;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        OriginalHtmlBody originalHtmlBody = new OriginalHtmlBody("hogehoge");
        OriginalHtml originalHtml = OriginalHtmlFactory.create(originalHtmlBody);
        originalHtmlService.save(originalHtml);

        OriginalStyleBody originalStyleBody = new OriginalStyleBody("hogehoge");
        OriginalStyle originalStyle = OriginalStyleFactory.create(originalStyleBody);
        originalStyleService.save(originalStyle);

        mockMvc.perform(get("/contents/private/" + menu.getMenuName().getValue()))
                .andExpect(status().isOk())
                .andExpect(view().name("contents/menu"));
    }

    @Test
    @WithMockCustomUser
    public void contentPrivateMenu2() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PRIVATE;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        mockMvc.perform(get("/contents/private/" + menu.getMenuName().getValue()))
                .andExpect(status().isOk())
                .andExpect(view().name("contents/menu"));
    }

    @Test
    @WithMockCustomUser
    public void menuSave() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        MenuSaveForm menuSaveForm = new MenuSaveForm();
        menuSaveForm.setName("hogehoge");
        menuSaveForm.setViewLimit(MenuLimit.PUBLIC.getId());

        mockMvc.perform(
                post("/menu")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("name",menuSaveForm.getName())
                        .param("viewLimit",menuSaveForm.getViewLimit().toString()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("success",true))
                .andExpect(status().is3xxRedirection());

        Assert.assertTrue(menuService.list().size() == 2);
    }

    @Test
    @WithMockCustomUser
    public void menuSave_fail() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        MenuSaveForm menuSaveForm = new MenuSaveForm();
        menuSaveForm.setName("menu");
        menuSaveForm.setViewLimit(MenuLimit.PUBLIC.getId());

        mockMvc.perform(
                post("/menu")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("name",menuSaveForm.getName())
                        .param("viewLimit",menuSaveForm.getViewLimit().toString()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("error",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void menuDelete() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        MenuName menuName2 = new MenuName("menu2");
        MenuLimit menuLimit2 = MenuLimit.PUBLIC;
        Menu menu2 = MenuFactory.create(menuName2,menuLimit2);
        menuService.save(menu2);

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        mockMvc.perform(
                post("/menu/" + menu2.getMenuId().getValue() + "/delete")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("success",true))
                .andExpect(status().is3xxRedirection());

        Assert.assertTrue(menuService.list().size() == 1);
    }

    @Test
    @WithMockCustomUser
    public void menuDelete_fail() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        mockMvc.perform(
                post("/menu/" + menu.getMenuId().getValue() + "/delete")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("error",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void menuUpdate() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        MenuUpdateForm menuUpdateForm = new MenuUpdateForm();
        menuUpdateForm.setMenuName("menu2");
        menuUpdateForm.setMenuViewLimit(MenuLimit.PRIVATE.getId());

        mockMvc.perform(
                post("/menu/" + menu.getMenuId().getValue() + "/update")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("menuName",menuUpdateForm.getMenuName())
                        .param("menuViewLimit",menuUpdateForm.getMenuViewLimit().toString()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("success",true))
                .andExpect(status().is3xxRedirection());

        Assert.assertTrue(menuService.getById(menu.getMenuId()).getMenuName().getValue().equals("menu2"));
    }

    @Test
    @WithMockCustomUser
    public void menuUpdate_fail() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        Menu menu = MenuFactory.create(menuName,menuLimit);
        menuService.save(menu);

        MenuName menuName2 = new MenuName("menu2");
        MenuLimit menuLimit2 = MenuLimit.PUBLIC;
        Menu menu2 = MenuFactory.create(menuName2,menuLimit2);
        menuService.save(menu2);

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        MenuUpdateForm menuUpdateForm = new MenuUpdateForm();
        menuUpdateForm.setMenuName("menu");
        menuUpdateForm.setMenuViewLimit(MenuLimit.PUBLIC.getId());

        mockMvc.perform(
                post("/menu/" + menu2.getMenuId().getValue() + "/update")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("menuName",menuUpdateForm.getMenuName())
                        .param("menuViewLimit",menuUpdateForm.getMenuViewLimit().toString()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("error",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void menuSort() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        MenuSortNumber menuSortNumber = new MenuSortNumber(1);
        Menu menu = MenuFactory.createWithSort(menuName,menuLimit,menuSortNumber);
        menuService.save(menu);

        MenuName menuName2 = new MenuName("menu2");
        MenuLimit menuLimit2 = MenuLimit.PUBLIC;
        MenuSortNumber menuSortNumber2 = new MenuSortNumber(2);
        Menu menu2 = MenuFactory.createWithSort(menuName2,menuLimit2,menuSortNumber2);
        menuService.save(menu2);

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        MenuSortForm menuSortForm = new MenuSortForm();
        menuSortForm.setFirstMenuId(menu.getMenuId().getValue());
        menuSortForm.setSecondMenuId(menu2.getMenuId().getValue());

        mockMvc.perform(
                post("/menu/" + menu.getMenuId().getValue() + "/sort")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("firstMenuId",menuSortForm.getFirstMenuId())
                        .param("secondMenuId",menuSortForm.getSecondMenuId()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("success",true))
                .andExpect(status().is3xxRedirection());

        Assert.assertTrue(menuService.getById(menu.getMenuId()).getMenuSortNumber().getValue() == 2);
    }

    @Test
    @WithMockCustomUser
    public void menuSort_fail() throws Exception {
        MenuName menuName = new MenuName("menu");
        MenuLimit menuLimit = MenuLimit.PUBLIC;
        MenuSortNumber menuSortNumber = new MenuSortNumber(1);
        Menu menu = MenuFactory.createWithSort(menuName,menuLimit,menuSortNumber);
        menuService.save(menu);

        MenuName menuName2 = new MenuName("menu2");
        MenuLimit menuLimit2 = MenuLimit.PUBLIC;
        MenuSortNumber menuSortNumber2 = new MenuSortNumber(2);
        Menu menu2 = MenuFactory.createWithSort(menuName2,menuLimit2,menuSortNumber2);

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        MenuSortForm menuSortForm = new MenuSortForm();
        menuSortForm.setFirstMenuId(menu.getMenuId().getValue());
        menuSortForm.setSecondMenuId(menu2.getMenuId().getValue());

        mockMvc.perform(
                post("/menu/" + menu.getMenuId().getValue() + "/sort")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("firstMenuId",menuSortForm.getFirstMenuId())
                        .param("secondMenuId",menuSortForm.getSecondMenuId()))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void sortPage() throws Exception {
        MenuName menuName = new MenuName("menu");
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

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        PageSortForm pageSortForm = new PageSortForm();
        pageSortForm.setFirstPageId(page.getPageId().getValue());
        pageSortForm.setSecondPageId(page2.getPageId().getValue());

        mockMvc.perform(
                post("/menu/" + menu.getMenuId().getValue() + "/pages/sort")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("firstPageId",pageSortForm.getFirstPageId())
                        .param("secondPageId",pageSortForm.getSecondPageId()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("success",true))
                .andExpect(status().is3xxRedirection());

        Assert.assertTrue(pageService.get(page.getPageId()).getPageSortNumber().getValue() == 2);
    }

    @Test
    @WithMockCustomUser
    public void sortPage_fail() throws Exception {
        MenuName menuName = new MenuName("menu");
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

        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        PageSortForm pageSortForm = new PageSortForm();
        pageSortForm.setFirstPageId(page.getPageId().getValue());
        pageSortForm.setSecondPageId(page2.getPageId().getValue());

        mockMvc.perform(
                post("/menu/" + menu.getMenuId().getValue() + "/pages/sort")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("firstPageId",pageSortForm.getFirstPageId())
                        .param("secondPageId",pageSortForm.getSecondPageId()))
                .andExpect(status().is3xxRedirection());
    }
}
