package com.bobjamin.kratosplugin.ui;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import static com.bobjamin.kratosplugin.utils.Constants.MAIN_TAB;

public class KratosToolWindowFactory implements ToolWindowFactory, DumbAware {
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        KratosToolWindow myToolWindow = new KratosToolWindow();
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(myToolWindow.getContent(), MAIN_TAB, false);
        toolWindow.getContentManager().addContent(content);
    }

}
