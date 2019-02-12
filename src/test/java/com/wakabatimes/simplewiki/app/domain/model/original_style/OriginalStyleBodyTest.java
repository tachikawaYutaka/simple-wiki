package com.wakabatimes.simplewiki.app.domain.model.original_style;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class OriginalStyleBodyTest {
    @Test
    public void createInstance_success() {
        OriginalStyleBody bodyContent = new OriginalStyleBody("hogehoge");
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_fail() {
        OriginalStyleBody bodyContent = new OriginalStyleBody("");
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_fail2() {
        String str = "A";
        String rep= StringUtils.repeat(str, 1000001);
        OriginalStyleBody bodyContent = new OriginalStyleBody(rep);
    }
}
