package com.bruce.dubboplugin.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@Builder
public class Dependency {

//    <groupId>{{groupId}}</groupId>
//    <artifactId>{{artifactId}}</artifactId>
//    {{#version}}
//    <version>{{version}}</version>
//    {{/version}}
//			<scope>runtime</scope>
//    {{#type}}
//    <type>{{type}}</type>
//    {{/type}}

    /**
     * Compile Scope.
     */
    public static final String SCOPE_COMPILE = "compile";

    /**
     * Compile Only Scope.
     */
    public static final String SCOPE_COMPILE_ONLY = "compileOnly";

    /**
     * Runtime Scope.
     */
    public static final String SCOPE_RUNTIME = "runtime";

    /**
     * Provided Scope.
     */
    public static final String SCOPE_PROVIDED = "provided";

    /**
     * Test Scope.
     */
    public static final String SCOPE_TEST = "test";

    /**
     * All scope types.
     */
    public static final List<String> SCOPE_ALL = Collections
            .unmodifiableList(Arrays.asList(SCOPE_COMPILE, SCOPE_RUNTIME,
                    SCOPE_COMPILE_ONLY, SCOPE_PROVIDED, SCOPE_TEST));

    private String groupId;

    private String artifactId;

    private String version;

    @Builder.Default
    private String scope = SCOPE_COMPILE;

    private String type;
}
