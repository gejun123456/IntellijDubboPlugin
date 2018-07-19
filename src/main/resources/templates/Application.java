package {{packageName}};

import org.springframework.boot.SpringApplication;

{{#isDubboClient}}
import javax.annotation.PostConstruct;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import {{dubboServiceName}};
{{/isDubboClient}}
{{^hasWeb}}
import java.util.concurrent.CountDownLatch;
{{/hasWeb}}


{{applicationImports}}
{{applicationAnnotations}}
@EnableDubboConfiguration
public class {{applicationName}} {

	{{#isDubboClient}}
	@Reference(version = "{{dubboServiceVersion}}"{{^embeddedZookeeper}}, url = "dubbo://localhost:20880"{{/embeddedZookeeper}})
  	private {{serviceSimpleName}} demoService;
	{{/isDubboClient}}

	public static void main(String[] args) {{^hasWeb}}throws InterruptedException{{/hasWeb}}{

		SpringApplication.run({{applicationName}}.class, args);

		{{^hasWeb}}
		new CountDownLatch(1).await(); //hold住应用，防止provider退出
		{{/hasWeb}}
	}
	
	{{#isDubboClient}}
    @PostConstruct
    public void init() {
    	String sayHello = demoService.sayHello("world");
    	System.err.println(sayHello);
    }
    {{/isDubboClient}}
}
