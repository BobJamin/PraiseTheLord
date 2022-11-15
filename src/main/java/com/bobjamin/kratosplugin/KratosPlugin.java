package com.bobjamin.kratosplugin;

import com.bobjamin.kratosplugin.services.CodeAnalysisService;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.PsiFile;

import static com.bobjamin.kratosplugin.utils.Constants.PLUGIN_NAME;

public class KratosPlugin extends AnAction {

    @Override
    public void update(AnActionEvent e) {
        // This will only allow the user to launch Kratos on a single file,
        // TODO: Allow the user to launch Kratos on an arborsecence (using PsiElement exploration)
        PsiFile file = e.getData(CommonDataKeys.PSI_FILE);
        e.getPresentation().setEnabled(file != null);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        // We are sure to receive a PSI_FILE due to the current update method
        PsiFile file = e.getRequiredData(CommonDataKeys.PSI_FILE);
        CodeAnalysisService codeAnalysisService = ApplicationManager.getApplication().getService(CodeAnalysisService.class);
        codeAnalysisService.run(file.getName(), file.getLanguage(), file.getText());
        ToolWindowManager.getInstance(e.getProject()).getToolWindow(PLUGIN_NAME).show(null);
    }
}
