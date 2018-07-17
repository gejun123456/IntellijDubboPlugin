package {{dubboProviderPackageName}};

import java.util.Date;

import {{dubboPackageName}}.{{serviceSimpleName}}

import com.alibaba.dubbo.config.annotation.Service;

@Service(version = "{{dubboServiceVersion}}")
public class {{serviceSimpleName}}Impl implements {{serviceSimpleName}} {

	@Override
    public String sayHello(String name) {
        return "Hello, " + name + ", " + new Date();
    }

}