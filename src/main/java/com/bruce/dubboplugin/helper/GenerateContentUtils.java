package com.bruce.dubboplugin.helper;

import com.bruce.dubboplugin.dto.Dependency;
import com.bruce.dubboplugin.dto.GenerateContentContext;
import com.bruce.dubboplugin.dto.UserChooseDependency;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.io.IOUtils;
import org.jetbrains.idea.maven.project.MavenProjectsManager;
import org.jetbrains.idea.maven.utils.MavenUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GenerateContentUtils {
    public static void generateFiles(GenerateContentContext contentContext) {
        //try to only use with file api, so that will be really easy to test
        Project project = contentContext.getProject();
        VirtualFile root = contentContext.getRoot();
        String dir = project.getBasePath();
        UserChooseDependency userChooseDependency = contentContext.getUserChooseDependency();
        Map<String, Object> model = resolveModel(userChooseDependency);
        File projectFile = new File(dir);
        if (!projectFile.exists()) {
            projectFile.mkdirs();
        }
        String language = "java";

        if (userChooseDependency.isUseKotlin()) {
            language = "kotlin";
        }


        String applicationName = userChooseDependency.getArtifactId() + "Application";

        String pacakgeName = userChooseDependency.getGroupId() + "." + userChooseDependency.getArtifactId();


        if (userChooseDependency.isUseGradle()) {
            writeText(new File(dir, "build.gradle"), TemplateUtils.processToString("gradle.ftl", null));
        } else {
            String process = TemplateRenderer.INSTANCE.process("starter-pom.xml", model);
            writeText(new File(dir, "pom.xml"), process);
        }

        String codeLocation = language;


        try {
            VfsUtil.createDirectories(dir + "/src/main/java");
            VfsUtil.createDirectories(dir + "/src/main/resources");
            VfsUtil.createDirectories(dir + "/src/test/java");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        File src = new File(new File(dir, "src/main/" + codeLocation),
                pacakgeName.replace(".", "/"));
        src.mkdirs();

        //create the main class for springboot application
        String extension = ("kotlin".equals(language) ? "kt" : language);
        write(new File(src, applicationName + "." + extension),
                "Application." + extension, model);

//        generateGitIgnore();
        root.refresh(false, true);

//        if (userChooseDependency.isUseMaven()) {
//            List<VirtualFile> pomFiles = MavenUtil.streamPomFiles(project, project.getBaseDir()).collect(Collectors.toList());
//            MavenProjectsManager.getInstance(project).addManagedFilesOrUnignore(pomFiles);
//        }

    }

    private static Map<String, Object> resolveModel(UserChooseDependency userChooseDependency) {
        Map<String, Object> model = Maps.newHashMap();
        model.put("war", false);

        model.put("kotlinSupport", false);

        if (userChooseDependency.isUseMaven()) {
            model.put("mavenBuild", true);
            model.put("mavenParentGroupId", "org.springframework.boot");
            model.put("mavenParentArtifactId", "spring-boot-starter-parent");
            // TODO: 7/14/2018 need config version from user input
            model.put("mavenParentVersion", "1.5.14.RELEASE");
            model.put("includeSpringBootBom", false);
        }
        List<Dependency> dependencies = extractDependencyFrom(userChooseDependency.getDependencyList());

        model.put("compileDependencies",
                filterDependencies(dependencies, Dependency.SCOPE_COMPILE));
        model.put("runtimeDependencies",
                filterDependencies(dependencies, Dependency.SCOPE_RUNTIME));
        model.put("compileOnlyDependencies",
                filterDependencies(dependencies, Dependency.SCOPE_COMPILE_ONLY));
        model.put("providedDependencies",
                filterDependencies(dependencies, Dependency.SCOPE_PROVIDED));
        model.put("testDependencies",
                filterDependencies(dependencies, Dependency.SCOPE_TEST));
        Map<String, String> buildPropertyJava = Maps.newLinkedHashMap();
        buildPropertyJava.put("java.version", "1.8");
        model.put("buildPropertiesVersions", buildPropertyJava.entrySet());

        Map<String, String> buildPropertyMaven = Maps.newLinkedHashMap();

        buildPropertyMaven.put("project.build.sourceEncoding", "UTF-8");
        buildPropertyMaven.put("project.reporting.outputEncoding", "UTF-8");

        model.put("buildPropertiesMaven", buildPropertyMaven.entrySet());

        model.put("dependencyManagementPluginVersion", "0.6.0.RELEASE");

        model.put("kotlinVersion", "1.2.20");

        model.put("isRelease", true);

        model.put("applicationImports", "import org.springframework.boot.autoconfigure.SpringBootApplication;");

        model.put("applicationAnnotations", "@SpringBootApplication");

        model.put("bootOneThreeAvailable", true);

        model.put("bootTwoZeroAvailable", false);

        model.put("springBootPluginName", "org.springframework.boot");

        model.put("newTestInfrastructure", true);

        model.put("servletInitializrImport", "import org.springframework.boot.web.support.SpringBootServletInitializer;");

        model.put("kotlinStdlibArtifactId", "kotlin-stdlib-jdk8");

        model.put("java8OrLater", "true");
        model.put("applicationName", userChooseDependency.getArtifactId() + "Application");
        model.put("artifactId", userChooseDependency.getArtifactId());
        model.put("baseDir", userChooseDependency.getArtifactId());
//        model.put("boms","{}");
        // TODO: 7/14/2018 need config them
        model.put("bootVersion", "1.5.14.RELEASE");
        model.put("build", "maven");
        model.put("buildProperties", "io.spring.initializr.generator.BuildProperties@62547c95");
        model.put("class", "class io.spring.initializr.generator.ProjectRequest");
        model.put("dependencies", "[]");
        model.put("description", "Demo project for Spring Boot");
        model.put("dubboServiceName", "com.example.HelloService");
        model.put("dubboServiceVersion", "1.0.0");
        model.put("dubboSide", "dubboServer");
        model.put("embeddedZookeeper", "true");
//        model.put("facets","[]");
        model.put("groupId", userChooseDependency.getGroupId());
        model.put("javaVersion", "1.8");
        if (userChooseDependency.isUseJava()) {
            model.put("language", "java");
        } else {
            model.put("language", "kotlin");
        }
        model.put("name", userChooseDependency.getArtifactId());
        model.put("packageName", userChooseDependency.getGroupId() + "." + userChooseDependency.getArtifactId());
        model.put("packaging", "jar");
//        model.put("parameters","{host=localhost:9097, connection=keep-alive, upgrade-insecure-requests=1, user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36, accept=text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8, referer=http://localhost:9097/, accept-encoding=gzip, deflate, br, accept-language=en,zh-CN;q=0.9,zh;q=0.8,ja;q=0.7,zh-TW;q=0.6,nl;q=0.5}");
        model.put("qos", "false");
        model.put("repositories", "{}");
        model.put("resolvedDependencies", dependencies);
        //don't know the use for it.
//        model.put("style","[dubbo, netty4, fastjson, commons-lang3]");
        model.put("type", "maven-project");
        model.put("version", "0.0.1-SNAPSHOT");
        return model;
    }

    private static Object filterDependencies(List<Dependency> dependencies, String scopeCompile) {
        return dependencies.stream().filter((dep) -> scopeCompile.equals(dep.getScope())).collect(Collectors.toList());
    }

    private static List<Dependency> extractDependencyFrom(List<String> dependencyList) {
        List<Dependency> dependencies = Lists.newArrayList();
        dependencies.add(Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build());
        for (String s : dependencyList) {
            dependencies.add(DependencyHelper.findDependency(s));
        }
        return dependencies;
    }

    private static void write(File file, String s, Map<String, Object> o) {
        writeText(file, TemplateRenderer.INSTANCE.process(s, o));
    }


    private static void writeText(File target, String body) {
        try {
            IOUtils.write(body, new FileOutputStream(target));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
