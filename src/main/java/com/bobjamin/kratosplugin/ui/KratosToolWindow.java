package com.bobjamin.kratosplugin.ui;

import com.bobjamin.kratosplugin.models.CodeReport;
import com.bobjamin.kratosplugin.models.CodeReportListener;
import com.bobjamin.kratosplugin.services.CodeAnalysisService;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;

public class KratosToolWindow implements CodeReportListener {

    private JButton hideToolWindowButton;
    private JLabel currentDate;
    private JPanel myToolWindowContent;
    private JRadioButton radioButton1;

    public KratosToolWindow(ToolWindow toolWindow) {
        hideToolWindowButton.addActionListener(e -> toolWindow.hide(null));
        CodeAnalysisService codeAnalysisService = ApplicationManager.getApplication().getService(CodeAnalysisService.class);
        codeAnalysisService.subscribe(this);
    }

    public JPanel getContent() {
        return myToolWindowContent;
    }

    @Override
    public void update(CodeReport codeReport) {
        currentDate.setText(String.format("WMC: %s\nTCC: %s\nATFD: %s", codeReport.getWmc(), codeReport.getTcc(), codeReport.getAtfd()));
    }
}
