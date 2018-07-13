import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.StringWriter;
import java.util.Map;

public class TemplateUtil {

    private static Configuration configuration;


    static {
        configuration = new Configuration(Configuration.getVersion());
        try {
            configuration.setClassLoaderForTemplateLoading(TemplateUtil.class.getClassLoader(),"templates");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
    }

    private static Log log = LogFactory.INSTANCE.getLogger(TemplateUtil.class);

    public static String processToString(String templateName, Map<String, Object> root) {
        try {
            Template template = configuration.getTemplate(templateName);
            StringWriter out = new StringWriter();
            template.process(root, out);
            String s = out.toString();
            s = s.replaceAll("\r\n","\n");
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("template process catch exception", e);
            throw new RuntimeException("process freemarker template catch exception", e);
        }
    }
}