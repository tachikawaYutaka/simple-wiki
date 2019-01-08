package com.wakabatimes.simplewiki.view.system;

import com.wakabatimes.simplewiki.SimpleWikiApplication;
import com.wakabatimes.simplewiki.WithMockCustomUser;
import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.model.system.SystemFactory;
import com.wakabatimes.simplewiki.app.domain.model.system.SystemName;
import com.wakabatimes.simplewiki.app.domain.model.user.*;
import com.wakabatimes.simplewiki.app.domain.service.body.BodyService;
import com.wakabatimes.simplewiki.app.domain.service.menu.MenuService;
import com.wakabatimes.simplewiki.app.domain.service.original_html.OriginalHtmlService;
import com.wakabatimes.simplewiki.app.domain.service.original_style.OriginalStyleService;
import com.wakabatimes.simplewiki.app.domain.service.page.PageService;
import com.wakabatimes.simplewiki.app.domain.service.system.SystemService;
import com.wakabatimes.simplewiki.app.domain.service.user.UserService;
import com.wakabatimes.simplewiki.app.interfaces.user.form.UserNameUpdateForm;
import com.wakabatimes.simplewiki.app.interfaces.user.form.UserPasswordUpdateForm;
import com.wakabatimes.simplewiki.app.interfaces.user.form.UserSaveForm;
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
public class UserAdminControllerTest {
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
    public void systemUsers() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        mockMvc.perform(get("/system/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("system/system_user"));
    }

    @Test
    @WithMockCustomUser
    public void systemUserAdd() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        UserSaveForm userSaveForm = new UserSaveForm();
        userSaveForm.setUserName("hogehoge");
        userSaveForm.setUserPassword("hogehoge");
        userSaveForm.setUserRole(UserRole.ROLE_EDITOR.getId());

        mockMvc.perform(
                post("/system/users")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("userName",userSaveForm.getUserName())
                        .param("userPassword",userSaveForm.getUserPassword())
                        .param("userRole",userSaveForm.getUserRole().toString()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("success",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void systemUserAdd_fail() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        UserSaveForm userSaveForm = new UserSaveForm();
        userSaveForm.setUserName("");
        userSaveForm.setUserPassword("hogehoge");
        userSaveForm.setUserRole(UserRole.ROLE_EDITOR.getId());

        mockMvc.perform(
                post("/system/users")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("userName",userSaveForm.getUserName())
                        .param("userPassword",userSaveForm.getUserPassword())
                        .param("userRole",userSaveForm.getUserRole().toString()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("error",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void systemUserNameMod() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        User user = UserFactory.create(userName, userPassword);
        userService.save(user);

        UserNameUpdateForm userNameUpdateForm = new UserNameUpdateForm();
        userNameUpdateForm.setUserName("hogehoge2");

        mockMvc.perform(
                post("/system/users/" + user.getUserId().getValue() + "/userName")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("userName",userNameUpdateForm.getUserName()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("success",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void systemUserNameMod_fail() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        User user = UserFactory.create(userName, userPassword);
        userService.save(user);

        UserNameUpdateForm userNameUpdateForm = new UserNameUpdateForm();
        userNameUpdateForm.setUserName("");

        mockMvc.perform(
                post("/system/users/" + user.getUserId().getValue() + "/userName")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("userName",userNameUpdateForm.getUserName()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("error",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void systemUserPasswordMod() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        User user = UserFactory.create(userName, userPassword);
        userService.save(user);

        UserPasswordUpdateForm userPasswordUpdateForm = new UserPasswordUpdateForm();
        userPasswordUpdateForm.setUserPassword("hogehoge2");

        mockMvc.perform(
                post("/system/users/" + user.getUserId().getValue() + "/userPassword")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("userPassword",userPasswordUpdateForm.getUserPassword()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("success",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void systemUserPasswordMod_fail() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        User user = UserFactory.create(userName, userPassword);
        userService.save(user);

        UserPasswordUpdateForm userPasswordUpdateForm = new UserPasswordUpdateForm();
        userPasswordUpdateForm.setUserPassword("");

        mockMvc.perform(
                post("/system/users/" + user.getUserId().getValue() + "/userPassword")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("userPassword",userPasswordUpdateForm.getUserPassword()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("error",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void systemUserDelete() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        User user = UserFactory.create(userName, userPassword);
        userService.save(user);

        mockMvc.perform(
                post("/system/users/" + user.getUserId().getValue() + "/delete")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("success",true))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockCustomUser
    public void systemUserDelete_fail() throws Exception {
        SystemName systemName = new SystemName("Simple Wiki");
        System system = SystemFactory.create(systemName);
        systemService.save(system);

        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        UserRole userRole = UserRole.ROLE_ADMIN;
        User user = UserFactory.create(userName, userPassword,userRole);
        userService.save(user);

        mockMvc.perform(
                post("/system/users/" + user.getUserId().getValue() + "/delete")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(flash().attribute("error",true))
                .andExpect(status().is3xxRedirection());
    }
}
