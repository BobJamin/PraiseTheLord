package com.bobjamin.kratosplugin;

import com.bobjamin.kratosplugin.services.CodeAnalysisService;
import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

import java.io.File;
import java.util.Arrays;

import static com.bobjamin.kratosplugin.utils.Constants.PLUGIN_NAME;

public class KratosPlugin extends AnAction {

    @Override
    public void update(AnActionEvent e) {
        PsiFile file = e.getData(CommonDataKeys.PSI_FILE);
        if(file != null) {
            e.getPresentation().setEnabled(file != null);
            return;
        }

        // if the user has selected multiple files
        PsiElement element = e.getData(CommonDataKeys.PSI_ELEMENT);
        if(element != null) {
            e.getPresentation().setEnabled(true);
            return;
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        // We are sure to receive a PSI_FILE due to the current update method
        PsiFile file = e.getData(CommonDataKeys.PSI_FILE);

        if(file != null) { // Single file
            CodeAnalysisService codeAnalysisService = ApplicationManager.getApplication().getService(CodeAnalysisService.class);
            codeAnalysisService.run(file.getName(), file.getLanguage(), file.getText());
            ToolWindowManager.getInstance(e.getProject()).getToolWindow(PLUGIN_NAME).show(null);
            return;
        }

        // Multiple files
        PsiElement element = e.getData(CommonDataKeys.PSI_ELEMENT);
        if(element != null) {
            PsiFile[] children = Arrays.stream(element.getChildren()).filter(psiElement -> psiElement instanceof PsiFile).toArray(PsiFile[]::new);
            CodeAnalysisService codeAnalysisService = ApplicationManager.getApplication().getService(CodeAnalysisService.class);
            codeAnalysisService.run(Arrays.stream(children).map(PsiFile::getName).toArray(String[]::new), Arrays.stream(children).map(PsiFile::getLanguage).toArray(Language[]::new), Arrays.stream(children).map(PsiFile::getText).toArray(String[]::new));
            ToolWindowManager.getInstance(e.getProject()).getToolWindow(PLUGIN_NAME).show(null);
            return;
        }
    }
}
