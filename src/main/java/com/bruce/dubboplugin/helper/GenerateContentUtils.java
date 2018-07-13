package com.bruce.dubboplugin.helper;

import com.bruce.dubboplugin.dto.GenerateContentContext;
import com.bruce.dubboplugin.dto.UserChooseDependency;
import com.github.rjeschke.txtmark.Run;
import com.intellij.ide.ui.EditorOptionsTopHitProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Map;

public class GenerateContentUtils {
    public static void generateFiles(GenerateContentContext contentContext) {
        //try to only use with file api, so that will be really easy to test
        Project project = contentContext.getProject();

        String dir = project.getBasePath();
        UserChooseDependency userChooseDependency = contentContext.getUserChooseDependency();
        File projectFile = new File(dir);
        if (!projectFile.exists()) {
            projectFile.mkdirs();
        }
        String language = "java";

        if (userChooseDependency.isUseKotlin()) {
            language = "kotlin";
        }


        String applicationName = userChooseDependency.getArtifactId();

        String pacakgeName = userChooseDependency.getGroupId();


        if (userChooseDependency.isUseGradle()) {
            writeText(new File(dir, "build.gradle"), TemplateUtils.processToString("gradle.ftl", null));
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
                "Application2." + extension, null);

//        generateGitIgnore();

        LocalFileSystem.getInstance().refreshWithoutFileWatcher(true);
    }

    private static void write(File file, String s, Map<String, Object> o) {
        writeText(file, TemplateUtils.processToString(s, o));
    }


    private static void writeText(File target, String body) {
        try {
            IOUtils.write(body, new FileOutputStream(target));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
