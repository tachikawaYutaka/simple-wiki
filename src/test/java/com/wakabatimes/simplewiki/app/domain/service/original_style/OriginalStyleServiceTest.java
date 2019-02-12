package com.wakabatimes.simplewiki.app.domain.service.original_style;

import com.wakabatimes.simplewiki.SimpleWikiApplication;
import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyle;
import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyleBody;
import com.wakabatimes.simplewiki.app.domain.model.original_style.OriginalStyleFactory;
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

import static org.hamcrest.CoreMatchers.is;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SimpleWikiApplication.class)
public class OriginalStyleServiceTest {
    @Autowired
    OriginalStyleService originalStyleService;

    @Autowired
    private JdbcOperations jdbcOperations; // 各テスト前処理用

    @Before
    public void eachBefore() {
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcOperations.execute("DELETE FROM original_style");
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=1");
    }

    @After
    public void eachAfter() {
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcOperations.execute("DELETE FROM original_style");
        jdbcOperations.execute("SET FOREIGN_KEY_CHECKS=1");
    }

    @Test
    public void save(){
        OriginalStyleBody originalStyleBody = new OriginalStyleBody("hogehoge");
        OriginalStyle originalStyle = OriginalStyleFactory.create(originalStyleBody);
        originalStyleService.save(originalStyle);
    }

    @Test(expected = RuntimeException.class)
    public void save_fail(){
        OriginalStyleBody originalStyleBody = new OriginalStyleBody("hogehoge");
        OriginalStyle originalStyle = OriginalStyleFactory.create(originalStyleBody);
        originalStyleService.save(originalStyle);

        OriginalStyleBody originalStyleBody2 = new OriginalStyleBody("hogehoge");
        OriginalStyle originalStyle2 = OriginalStyleFactory.create(originalStyleBody2);
        originalStyleService.save(originalStyle2);
    }

    @Test
    public void update() {
        OriginalStyleBody originalStyleBody = new OriginalStyleBody("hogehoge");
        OriginalStyle originalStyle = OriginalStyleFactory.create(originalStyleBody);
        originalStyleService.save(originalStyle);

        OriginalStyleBody newBody = new OriginalStyleBody("hogehoge2");
        OriginalStyle newStyle = new OriginalStyle(originalStyle.getOriginalStyleId(),newBody);
        originalStyleService.update(newStyle);

        OriginalStyle getStyle = originalStyleService.get();
        Assert.assertThat(getStyle.getOriginalStyleBody().getValue(),is("hogehoge2"));
    }

    @Test
    public void delete() {
        OriginalStyleBody originalStyleBody = new OriginalStyleBody("hogehoge");
        OriginalStyle originalStyle = OriginalStyleFactory.create(originalStyleBody);
        originalStyleService.save(originalStyle);

        originalStyleService.delete();

        boolean exist = originalStyleService.exist();
        Assert.assertFalse(exist);
    }

    @Test(expected = RuntimeException.class)
    public void delete_fail() {
        originalStyleService.delete();
    }

    @Test
    public void existTrue() {
        OriginalStyleBody originalStyleBody = new OriginalStyleBody("hogehoge");
        OriginalStyle originalStyle = OriginalStyleFactory.create(originalStyleBody);
        originalStyleService.save(originalStyle);
        boolean exist = originalStyleService.exist();
        Assert.assertTrue(exist);
    }

    @Test
    public void  existFalse() {
        boolean exist = originalStyleService.exist();
        Assert.assertFalse(exist);
    }

    @Test
    public void get() {
        OriginalStyleBody originalStyleBody = new OriginalStyleBody("hogehoge");
        OriginalStyle originalStyle = OriginalStyleFactory.create(originalStyleBody);
        originalStyleService.save(originalStyle);

        OriginalStyle getStyle = originalStyleService.get();
        Assert.assertThat(getStyle.getOriginalStyleBody().getValue(),is("hogehoge"));
    }
}
