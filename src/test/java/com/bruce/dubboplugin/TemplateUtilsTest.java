package com.bruce.dubboplugin;

import com.bruce.dubboplugin.helper.TemplateUtils;
import org.junit.Test;

public class TemplateUtilsTest {
    @Test
    public void testTemplateUtils(){
        String s = TemplateUtils.processToString("gradle.ftl", null);
        System.out.println(s);
    }
}
