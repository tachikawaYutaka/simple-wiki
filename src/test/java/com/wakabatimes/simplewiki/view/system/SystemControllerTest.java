package com.wakabatimes.simplewiki.view.system;

import com.wakabatimes.simplewiki.SimpleWikiApplication;
import com.wakabatimes.simplewiki.WithMockCustomUser;
import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtml;
import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtmlBody;
import com.wakabatimes.simplewiki.app.domain.model.original_html.OriginalHtmlFactory;
import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyle;
import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyleBody;
import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyleFactory;
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
import com.wakabatimes.simplewiki.app.interfaces.original_html.form.OriginalHtmlUpdateForm;
import com.wakabatimes.simplewiki.app.interfaces.original_style.form.OriginalStyleUpdateForm;
import com.wakabatimes.simplewiki.app.interfaces.system.form.SystemNameUpdateForm;
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
public class SystemControllerTest {
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
    public void systemName() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        mockMvc.perform(get("/system/name"))
                .andExpect(status().isOk())
                .andExpect(view().name("system/system_name"));
    }

    @Test
    @WithMockCustomUser
    public void systemNameUpdate() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        SystemNameUpdateForm systemNameUpdateForm = new SystemNameUpdateForm();
        systemNameUpdateForm.setSystemName("hogehoge2");

        mockMvc.perform(
                post("/system/name")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("systemName",systemNameUpdateForm.getSystemName()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("success",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void systemNameUpdate_fail() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        SystemNameUpdateForm systemNameUpdateForm = new SystemNameUpdateForm();
        systemNameUpdateForm.setSystemName("");

        mockMvc.perform(
                post("/system/name")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("systemName",systemNameUpdateForm.getSystemName()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("error",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void systemStyle() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        mockMvc.perform(get("/system/style"))
                .andExpect(status().isOk())
                .andExpect(view().name("system/system_style"));
    }

    @Test
    @WithMockCustomUser
    public void systemStyle_exist() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        OriginalStyleBody originalStyleBody = new OriginalStyleBody("hogehoge");
        OriginalStyle originalStyle = OriginalStyleFactory.create(originalStyleBody);
        originalStyleService.save(originalStyle);

        mockMvc.perform(get("/system/style"))
                .andExpect(status().isOk())
                .andExpect(view().name("system/system_style"));
    }

    @Test
    @WithMockCustomUser
    public void systemStyleUpdate() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        OriginalStyleUpdateForm originalStyleUpdateForm = new OriginalStyleUpdateForm();
        originalStyleUpdateForm.setBody("hogehoge2");

        mockMvc.perform(
                post("/system/style")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("body",originalStyleUpdateForm.getBody()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("success",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void systemStyleUpdate_exist() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        OriginalStyleBody originalStyleBody = new OriginalStyleBody("hogehoge");
        OriginalStyle originalStyle = OriginalStyleFactory.create(originalStyleBody);
        originalStyleService.save(originalStyle);

        OriginalStyleUpdateForm originalStyleUpdateForm = new OriginalStyleUpdateForm();
        originalStyleUpdateForm.setBody("hogehoge2");

        mockMvc.perform(
                post("/system/style")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("body",originalStyleUpdateForm.getBody()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("success",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void systemStyleUpdate_fail() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        OriginalStyleUpdateForm originalStyleUpdateForm = new OriginalStyleUpdateForm();
        originalStyleUpdateForm.setBody("");

        mockMvc.perform(
                post("/system/style")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("body",originalStyleUpdateForm.getBody()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("error",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void systemHtml() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        mockMvc.perform(get("/system/html"))
                .andExpect(status().isOk())
                .andExpect(view().name("system/system_html"));
    }

    @Test
    @WithMockCustomUser
    public void systemHtml_exist() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        OriginalHtmlBody originalHtmlBody = new OriginalHtmlBody("hogehoge");
        OriginalHtml originalHtml = OriginalHtmlFactory.create(originalHtmlBody);
        originalHtmlService.save(originalHtml);

        mockMvc.perform(get("/system/html"))
                .andExpect(status().isOk())
                .andExpect(view().name("system/system_html"));
    }

    @Test
    @WithMockCustomUser
    public void systemHtmlUpdate() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        OriginalHtmlBody originalHtmlBody = new OriginalHtmlBody("hogehoge");
        OriginalHtml originalHtml = OriginalHtmlFactory.create(originalHtmlBody);
        originalHtmlService.save(originalHtml);

        OriginalHtmlUpdateForm originalHtmlUpdateForm = new OriginalHtmlUpdateForm();
        originalHtmlUpdateForm.setBody("hogehoge2");

        mockMvc.perform(
                post("/system/html")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("body",originalHtmlUpdateForm.getBody()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("success",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void systemHtmlUpdate_exist() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        OriginalHtmlUpdateForm originalHtmlUpdateForm = new OriginalHtmlUpdateForm();
        originalHtmlUpdateForm.setBody("hogehoge2");

        mockMvc.perform(
                post("/system/html")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("body",originalHtmlUpdateForm.getBody()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("success",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void systemHtmlUpdate_fail() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        OriginalHtmlUpdateForm originalHtmlUpdateForm = new OriginalHtmlUpdateForm();
        originalHtmlUpdateForm.setBody("");

        mockMvc.perform(
                post("/system/html")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("body",originalHtmlUpdateForm.getBody()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("error",true))
                .andExpect(status().is3xxRedirection());
    }
}
