package com.bruce.dubboplugin.helper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.StringWriter;
import java.util.Map;

public class TemplateUtils {
    private static Configuration configuration;


    static {
        configuration = new Configuration(Configuration.getVersion());
        try {
            configuration.setClassLoaderForTemplateLoading(TemplateUtils.class.getClassLoader(),"templates");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
    }


    public static String processToString(String templateName, Map<String, Object> root) {
        try {
            Template template = configuration.getTemplate(templateName);
            StringWriter out = new StringWriter();
            template.process(root, out);
            String s = out.toString();
            s = s.replaceAll("\r\n","\n");
            return s;
        } catch (Exception e) {
            throw new RuntimeException("process freemarker template catch exception", e);
        }
    }
}
