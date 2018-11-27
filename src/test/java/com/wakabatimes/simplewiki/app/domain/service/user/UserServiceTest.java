package com.wakabatimes.simplewiki.app.domain.service.user;

import com.wakabatimes.simplewiki.SimpleWikiApplication;
import com.wakabatimes.simplewiki.app.domain.model.user.User;
import com.wakabatimes.simplewiki.app.domain.model.user.UserFactory;
import com.wakabatimes.simplewiki.app.domain.model.user.UserName;
import com.wakabatimes.simplewiki.app.domain.model.user.UserPassword;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
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
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private JdbcOperations jdbcOperations; // 各テスト前処理用
    @Before
    public void eachBefore() {
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcOperations.execute("DELETE FROM wiki_user");
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=1");
    }

    @After
    public void eachAfter() {
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcOperations.execute("DELETE FROM wiki_user");
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=1");
    }

    @Test
    public void save(){
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        User user = UserFactory.create(userName, userPassword);
        userService.save(user);
    }

    @Test
    public void delete(){
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        User user = UserFactory.create(userName, userPassword);
        userService.save(user);
        userService.delete(user.getUserId(),user.getUserName());
    }

    @Test
    public void passwordUpdate(){
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        User user = UserFactory.create(userName, userPassword);
        userService.save(user);
        UserPassword newPassword = new UserPassword("hogehoge2");
        userService.passwordUpdate(user.getUserName(),newPassword);
    }

    @Test
    public void nameUpdate(){
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        User user = UserFactory.create(userName, userPassword);
        userService.save(user);
        UserName newName = new UserName("hogehoge2");
        userService.nameUpdate(user.getUserName(),newName);
    }

    @Test
    public void get(){
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        User user = UserFactory.create(userName, userPassword);
        userService.save(user);
        User getUser = userService.get(userName);
        assertNotNull(getUser);
    }
}
