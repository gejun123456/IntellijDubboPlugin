package {{packageName}};

import org.springframework.boot.SpringApplication;

{{#isDubboClient}}
import javax.annotation.PostConstruct;
import com.alibaba.dubbo.config.annotation.Reference;
import {{dubboServiceName}};
{{/isDubboClient}}
{{^hasWeb}}
import java.util.concurrent.CountDownLatch;
{{/hasWeb}}


{{applicationImports}}

{{applicationAnnotations}}
public class {{applicationName}} {

	{{#isDubboClient}}
	@Reference(version = "{{dubboServiceVersion}}"{{^embeddedZookeeper}}, url = "dubbo://localhost:20880"{{/embeddedZookeeper}})
  	private {{serviceSimpleName}} demoService;
	{{/isDubboClient}}

	public static void main(String[] args) {{^hasWeb}}throws InterruptedException{{/hasWeb}}{
		{{#isDubboServer}}{{#embeddedZookeeper}}
		// start embedded zookeeper server
		new EmbeddedZooKeeper(2181, false).start();

		{{/embeddedZookeeper}}{{/isDubboServer}}
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
