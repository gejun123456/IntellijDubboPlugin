package com.bruce.dubboplugin;

import com.intellij.ide.util.projectWizard.*;
import com.intellij.openapi.module.JavaModuleType;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.StdModuleTypes;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.JavaSdk;
import com.intellij.openapi.projectRoots.SdkTypeId;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class DubboPluginModuleBuilder extends ModuleBuilder implements SourcePathsBuilder {
    @Override
    public void setupRootModel(ModifiableRootModel rootModel) throws ConfigurationException {
        Project project = rootModel.getProject();

        VirtualFile root = createAndGetContentEntry();

        if(myJdk!=null){
            rootModel.setSdk(myJdk);
        } else {
        rootModel.inheritSdk();
    }

        System.out.println("nimeiba");

}

    private VirtualFile createAndGetContentEntry() {
        String path = FileUtil.toSystemDependentName(getContentEntryPath());
        new File(path).mkdirs();
        return LocalFileSystem.getInstance().refreshAndFindFileByPath(path);
    }

    @Override
    public ModuleType getModuleType() {
        return StdModuleTypes.JAVA;
    }

    @Override
    public List<Pair<String, String>> getSourcePaths() throws ConfigurationException {
        return Collections.emptyList();
    }


    @Override
    public void setSourcePaths(List<Pair<String, String>> sourcePaths) {

    }

    @Override
    public void addSourcePath(Pair<String, String> sourcePathInfo) {

    }

    @Override
    public String getGroupName() {
        return "Dubbo";
    }

    @Override
    public String getPresentableName() {
        return "Dubbo";
    }

    @Override
    public String getParentGroup() {
        return JavaModuleType.BUILD_TOOLS_GROUP;
    }

    @Override
    public int getWeight() {
        return JavaModuleBuilder.BUILD_SYSTEM_WEIGHT;
    }


    @Override
    public boolean isSuitableSdkType(SdkTypeId sdkType) {
        return sdkType== JavaSdk.getInstance();
    }

    @Nullable
    @Override
    public String getBuilderId() {
        return getClass().getName();
    }
}
