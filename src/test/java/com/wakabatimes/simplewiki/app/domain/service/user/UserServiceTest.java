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

    @Test(expected = RuntimeException.class)
    public void save_fail(){
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        User user = UserFactory.create(userName, userPassword);
        userService.save(user);

        UserName userName2 = new UserName("hogehoge");
        UserPassword userPassword2 = new UserPassword("hogehoge");
        User user2 = UserFactory.create(userName2, userPassword2);
        userService.save(user2);
    }

    @Test
    public void delete(){
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        User user = UserFactory.create(userName, userPassword);
        userService.save(user);
        userService.delete(user.getUserId(),user.getUserName());
    }

    @Test(expected = RuntimeException.class)
    public void delete_fail(){
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        User user = UserFactory.create(userName, userPassword);
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

    @Test(expected = RuntimeException.class)
    public void passwordUpdate_fail(){
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        User user = UserFactory.create(userName, userPassword);
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

    @Test(expected = RuntimeException.class)
    public void nameUpdate_fail(){
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        User user = UserFactory.create(userName, userPassword);
        userService.save(user);

        UserName userName2 = new UserName("hogehoge2");
        UserPassword userPassword2 = new UserPassword("hogehoge2");
        User user2 = UserFactory.create(userName2, userPassword2);
        userService.save(user2);

        UserName newName = new UserName("hogehoge2");
        userService.nameUpdate(user.getUserName(),newName);
    }

    @Test(expected = RuntimeException.class)
    public void nameUpdate_fail2(){
        UserName userName = new UserName("hogehoge");
        UserPassword userPassword = new UserPassword("hogehoge");
        User user = UserFactory.create(userName, userPassword);
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
