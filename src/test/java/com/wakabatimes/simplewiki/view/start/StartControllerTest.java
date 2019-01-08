package com.wakabatimes.simplewiki.view.start;

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
import com.wakabatimes.simplewiki.app.domain.model.user.User;
import com.wakabatimes.simplewiki.app.domain.model.user.UserFactory;
import com.wakabatimes.simplewiki.app.domain.model.user.UserName;
import com.wakabatimes.simplewiki.app.domain.model.user.UserPassword;
import com.wakabatimes.simplewiki.app.domain.service.body.BodyService;
import com.wakabatimes.simplewiki.app.domain.service.menu.MenuService;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import com.wakabatimes.simplewiki.app.domain.service.system.SystemService;
import com.wakabatimes.simplewiki.app.domain.service.user.UserService;
import com.wakabatimes.simplewiki.app.interfaces.body.form.BodySaveForm;
import com.wakabatimes.simplewiki.app.interfaces.user.form.UserInitializeForm;
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
public class StartControllerTest {
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
    public void start() throws Exception {
        mockMvc.perform(get("/start"))
                .andExpect(status().isOk())
                .andExpect(view().name("start/start"));
    }

    @Test
    public void start_redirect() throws Exception {
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        User user = UserFactory.create(userName, userPassword);
        userService.save(user);

        mockMvc.perform(get("/start"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void editSave() throws Exception {
        UserInitializeForm initializeForm = new UserInitializeForm();
        initializeForm.setSystemName("hogehoge");
        initializeForm.setUserName("hogehoge");
        initializeForm.setUserPassword("hogehoge");
        mockMvc.perform(
                post("/start")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("systemName",initializeForm.getSystemName())
                        .param("userName",initializeForm.getUserName())
                        .param("userPassword",initializeForm.getUserPassword()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("success",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void editSave_fail() throws Exception {
        UserInitializeForm initializeForm = new UserInitializeForm();
        initializeForm.setSystemName("あああああ");
        initializeForm.setUserName("hogehoge");
        initializeForm.setUserPassword("hogehoge");
        mockMvc.perform(
                post("/start")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("systemName",initializeForm.getSystemName())
                        .param("userName",initializeForm.getUserName())
                        .param("userPassword",initializeForm.getUserPassword()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("error",true))
                .andExpect(status().is3xxRedirection());
    }
}
