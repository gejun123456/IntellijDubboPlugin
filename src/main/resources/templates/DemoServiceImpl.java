package {{dubboProviderPackageName}};

import java.util.Date;

import {{dubboPackageName}}.{{serviceSimpleName}};

import com.alibaba.dubbo.config.annotation.Service;

import org.springframework.stereotype.Component;

@Service(version = "{{dubboServiceVersion}}")
@Component
public class {{serviceSimpleName}}Impl implements {{serviceSimpleName}} {

	@Override
    public String sayHello(String name) {
        return "Hello, " + name + ", " + new Date();
    }

}