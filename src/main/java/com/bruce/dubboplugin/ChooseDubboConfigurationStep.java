package com.bruce.dubboplugin;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.wizard.StepAdapter;
import com.intellij.openapi.Disposable;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ChooseDubboConfigurationStep extends ModuleWizardStep implements Disposable {

    private DubboPluginModuleBuilder myBuilder;


    public ChooseDubboConfigurationStep(DubboPluginModuleBuilder builder, @Nullable StepAdapter stepAdapter) {
        this.myBuilder = builder;
    }

    @Override
    public JComponent getComponent() {
        JPanel jpanel = new JPanel();
        jpanel.add(new JTextField("nimeiya"));
        return jpanel;
    }

    @Override
    public void updateDataModel() {

    }

    @Override
    public void dispose() {

    }
}
