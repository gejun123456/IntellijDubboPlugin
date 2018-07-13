package com.bruce.dubboplugin;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.project.Project;

import javax.swing.*;

public class DubboModuleWizardStep extends ModuleWizardStep {

    private Project myProject;

    private WizardContext myContext;

    private DubboPluginModuleBuilder myBuilder;
    public DubboModuleWizardStep(DubboPluginModuleBuilder builder, WizardContext context,boolean includeArtifacts){
        myProject = context.getProject();
        myBuilder = builder;
        myContext = context;

    }

    @Override
    public JComponent getComponent() {
        JPanel panel = new JPanel();
        panel.add(new JTextField("helloMan"));
        return panel;
    }

    @Override
    public void updateDataModel() {
        System.out.println("model is updated");
    }
}
