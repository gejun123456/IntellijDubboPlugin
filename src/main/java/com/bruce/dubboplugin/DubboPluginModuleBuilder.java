package com.bruce.dubboplugin;

import com.bruce.dubboplugin.dto.GenerateContentContext;
import com.bruce.dubboplugin.dto.PreConfig;
import com.bruce.dubboplugin.dto.UserChooseDependency;
import com.bruce.dubboplugin.form.ChooseDubboConfigurationStep;
import com.bruce.dubboplugin.form.DubboModuleWizardStep;
import com.bruce.dubboplugin.helper.GenerateContentUtils;
import com.google.gson.Gson;
import com.intellij.ide.util.projectWizard.*;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.JavaModuleType;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.StdModuleTypes;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.JavaSdk;
import com.intellij.openapi.projectRoots.SdkTypeId;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
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

    private PreConfig preConfig;

    private UserChooseDependency userChooseDependency;

    @Override
    public void setupRootModel(ModifiableRootModel rootModel) throws ConfigurationException {

        String s = new Gson().toJson(userChooseDependency);
        System.out.println("set up root model json is:" + s);
        Project project = rootModel.getProject();
        VirtualFile root = createAndGetContentEntry();
        rootModel.addContentEntry(root);
        if (myJdk != null) {
            rootModel.setSdk(myJdk);
        } else {
            rootModel.inheritSdk();
        }
        System.out.println("start to create folder in path");


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
        return sdkType == JavaSdk.getInstance();
    }


    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull ModulesProvider modulesProvider) {
        return new ModuleWizardStep[]{
                new DubboModuleWizardStep(this, wizardContext, !wizardContext.isNewWizard())
        };
    }

    @Nullable
    @Override
    public ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
        return new ChooseDubboConfigurationStep(this, null);
    }

    @Override
    public ModuleWizardStep modifyStep(SettingsStep settingsStep) {
        return super.modifyStep(settingsStep);
    }

    @Nullable
    @Override
    public String getBuilderId() {
        return getClass().getName();
    }


    public PreConfig getPreConfig() {
        return preConfig;
    }

    public void setPreConfig(PreConfig preConfig) {
        this.preConfig = preConfig;
    }

    @Nullable
    @Override
    public ModuleWizardStep modifySettingsStep(@NotNull SettingsStep settingsStep) {
        ModuleNameLocationSettings moduleNameLocationSettings = settingsStep.getModuleNameLocationSettings();
        if (moduleNameLocationSettings != null && userChooseDependency != null && userChooseDependency.getArtifactId() != null) {
            moduleNameLocationSettings.setModuleName(userChooseDependency.getArtifactId());
//            moduleNameLocationSettings.setModuleContentRoot(moduleNameLocationSettings.getModuleContentRoot() + "/" + userChooseDependency.getArtifactId());
        }
        return super.modifySettingsStep(settingsStep);
    }

    public UserChooseDependency getUserChooseDependency() {
        return userChooseDependency;
    }

    public void setUserChooseDependency(UserChooseDependency userChooseDependency) {
        this.userChooseDependency = userChooseDependency;
    }


    @Nullable
    @Override
    public Project createProject(String name, String path) {
        GenerateContentContext contentContext = new GenerateContentContext();
        contentContext.setUserChooseDependency(userChooseDependency);
        contentContext.setRootPath(path);
        GenerateContentUtils.generateFiles(contentContext);
        return super.createProject(name,path);
    }
}
