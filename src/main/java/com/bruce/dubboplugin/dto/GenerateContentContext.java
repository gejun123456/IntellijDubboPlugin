package com.bruce.dubboplugin.dto;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateContentContext {
    private Project project;

    private UserChooseDependency userChooseDependency;

    private VirtualFile root;
}
