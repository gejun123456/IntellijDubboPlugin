package com.bruce.dubboplugin.form;
import com.bruce.dubboplugin.dto.Dependency;
import com.bruce.dubboplugin.dto.DependencyConstant;
import com.google.common.collect.Lists;

import com.bruce.dubboplugin.DubboPluginModuleBuilder;
import com.bruce.dubboplugin.dto.PreConfig;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.wizard.StepAdapter;
import com.intellij.openapi.Disposable;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseDubboConfigurationStep extends ModuleWizardStep implements Disposable {

    private DubboPluginModuleBuilder myBuilder;
    private JPanel panel1;
    private JRadioButton dubboSpringBootMybatisMysqlRadioButton;
    private JRadioButton createNewRadioButton;


    public ChooseDubboConfigurationStep(DubboPluginModuleBuilder builder, @Nullable StepAdapter stepAdapter) {
        this.myBuilder = builder;
        initComponents();
    }

    private void initComponents() {
        ButtonGroup group = new ButtonGroup();
        group.add(dubboSpringBootMybatisMysqlRadioButton);
        group.add(createNewRadioButton);
        final PreConfig preConfig = new PreConfig();
        preConfig.setDependencies(Lists.newArrayList(DependencyConstant.MYBAITS,DependencyConstant.MYSQL
                ,DependencyConstant.HIKARI,DependencyConstant.ZOOKEEPER,DependencyConstant.PAGE_HELPER));
        myBuilder.setPreConfig(preConfig);
        dubboSpringBootMybatisMysqlRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preConfig.setDependencies(Lists.newArrayList(DependencyConstant.MYBAITS,DependencyConstant.MYSQL
                ,DependencyConstant.HIKARI,DependencyConstant.ZOOKEEPER,DependencyConstant.PAGE_HELPER));
                myBuilder.setPreConfig(preConfig);
            }
        });

        createNewRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preConfig.setDependencies(Lists.newArrayList());
                myBuilder.setPreConfig(preConfig);
            }
        });
    }

    @Override
    public JComponent getComponent() {
        return panel1;
    }

    @Override
    public void updateDataModel() {

    }

    @Override
    public void dispose() {

    }
}
