package com.bruce.dubboplugin.form;

import com.bruce.dubboplugin.DubboPluginModuleBuilder;
import com.bruce.dubboplugin.dto.DependencyConstant;
import com.bruce.dubboplugin.dto.PreConfig;
import com.bruce.dubboplugin.dto.UserChooseDependency;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DubboModuleWizardStep extends ModuleWizardStep {

    private Project myProject;

    private WizardContext myContext;

    private DubboPluginModuleBuilder myBuilder;
    private JComboBox mavenGradleCombox;
    private JComboBox javaKotlinCombox;
    private JComboBox bootVersionCombox;
    private JTextField groupIdText;
    private JTextField artifactIdText;
    private JCheckBox mybatisCheckBox;
    private JCheckBox redisCheckBox;
    private JCheckBox mySqlCheckBox;
    private JCheckBox lombokCheckBox;
    private JCheckBox fastJsonCheckBox;
    private JCheckBox rabbitMqCheckBox;
    private JCheckBox commonsLang3CheckBox;
    private JCheckBox retryCheckBox;
    private JCheckBox zookeeperCheckBox;
    private JCheckBox hikariCheckBox;
    private JPanel myPanel;
    private JCheckBox cosumerCheckBox;
    private JCheckBox providerCheckBox;
    private JTextField demoApiTextField;
    private JTextField demoProviderTextField;


    private List<JCheckBox> myJCheckBoxList = Lists.newArrayList();
    private Map<String, JCheckBox> myDependecyToCheckBoxMap = Maps.newHashMap();
    private Map<JCheckBox, String> myJcheckBoxToDependencyMap = Maps.newHashMap();

    public DubboModuleWizardStep(DubboPluginModuleBuilder builder, WizardContext context, boolean includeArtifacts) {
        myProject = context.getProject();
        myBuilder = builder;
        myContext = context;
        System.out.println("start to init wizard step");
        initCheckBoxAndMap();
    }

    private void initCheckBoxAndMap() {
        addForOneCheckBox(mybatisCheckBox, DependencyConstant.MYBAITS);
        addForOneCheckBox(redisCheckBox, DependencyConstant.REDIS);
        addForOneCheckBox(mySqlCheckBox, DependencyConstant.MYSQL);
        addForOneCheckBox(lombokCheckBox, DependencyConstant.LOMBOK);
        addForOneCheckBox(fastJsonCheckBox, DependencyConstant.FASTJSON);
        addForOneCheckBox(rabbitMqCheckBox, DependencyConstant.RABBIT_MQ);
        addForOneCheckBox(commonsLang3CheckBox, DependencyConstant.COMMON_LANGS_3);
        addForOneCheckBox(retryCheckBox, DependencyConstant.SPRING_RETRY);
        addForOneCheckBox(zookeeperCheckBox, DependencyConstant.ZOOKEEPER);
//        addForOneCheckBox(notImplementedCheckBox, DependencyConstant.);
    }

    private void addForOneCheckBox(JCheckBox myCheckBox, String dependencyName) {
        myJCheckBoxList.add(myCheckBox);
        myDependecyToCheckBoxMap.put(dependencyName, myCheckBox);
        myJcheckBoxToDependencyMap.put(myCheckBox, dependencyName);
    }

    @Override
    public JComponent getComponent() {
        return myPanel;
    }

    @Override
    public void updateStep() {
        for (JCheckBox jCheckBox : myJCheckBoxList) {
            jCheckBox.setSelected(false);
        }
        List<String> dependencies = myBuilder.getPreConfig().getDependencies();
        if (!dependencies.isEmpty()) {
            for (String dependency : dependencies) {
                if (myDependecyToCheckBoxMap.containsKey(dependency)) {
                    myDependecyToCheckBoxMap.get(dependency).setSelected(true);
                }
            }
        }
    }

    @Override
    public void updateDataModel() {
        UserChooseDependency dependency = new UserChooseDependency();
        ArrayList<String> dependencyList = Lists.newArrayList();
        dependency.setDependencyList(dependencyList);
        if (mavenGradleCombox.getSelectedItem().equals("Maven")) {
            dependency.setUseMaven(true);
        } else {
            dependency.setUseGradle(true);
        }

        if (javaKotlinCombox.getSelectedItem().equals("Java")) {
            dependency.setUseJava(true);
        } else {
            dependency.setUseKotlin(true);
        }

        dependency.setBootVersion((String) bootVersionCombox.getSelectedItem());
        for (JCheckBox jCheckBox : myJCheckBoxList) {
            if(jCheckBox.isSelected()) {
                String s = myJcheckBoxToDependencyMap.get(jCheckBox);
                dependencyList.add(s);
            }
        }

        dependency.setGroupId(groupIdText.getText());
        dependency.setArtifactId(artifactIdText.getText());
        myBuilder.setUserChooseDependency(dependency);
    }
}
