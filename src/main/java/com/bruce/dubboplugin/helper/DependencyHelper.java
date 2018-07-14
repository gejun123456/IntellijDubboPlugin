package com.bruce.dubboplugin.helper;

import com.bruce.dubboplugin.dto.Dependency;
import com.bruce.dubboplugin.dto.DependencyConstant;

import java.util.HashMap;
import java.util.Map;

public class DependencyHelper {
    private static Map<String, Dependency> dependencyMap = new HashMap<String, Dependency>() {{
        put(DependencyConstant.MYBAITS, Dependency.builder().groupId("org.mybatis.spring.boot").artifactId("mybatis-spring-boot-starter").version("1.3.2").build());
        put(DependencyConstant.DUBBO, Dependency.builder().groupId("com.alibaba.boot").artifactId("dubbo-spring-boot-starter").version("0.1.0").build());
        put(DependencyConstant.FASTJSON, Dependency.builder().groupId("com.alibaba").artifactId("fastjson").version("1.2.47").build());
        put(DependencyConstant.COMMON_LANGS_3, Dependency.builder().groupId("org.apache.commons").artifactId("commons-lang3").version("3.7").build());

    }};

    public static Dependency findDependency(String dependency) {
        return dependencyMap.get(dependency);
    }

}
