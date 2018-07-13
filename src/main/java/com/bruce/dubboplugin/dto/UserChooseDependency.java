package com.bruce.dubboplugin.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserChooseDependency {
    private boolean useMaven;

    private boolean useGradle;

    private String bootVersion;

    private String groupId;

    private String artifactId;

    private boolean useJava;

    private boolean useKotlin;

    private List<String> dependencyList;

}
