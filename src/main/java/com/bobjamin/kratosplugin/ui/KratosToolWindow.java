package com.bobjamin.kratosplugin.ui;

import com.bobjamin.kratosplugin.models.CodeReport;
import com.bobjamin.kratosplugin.models.CodeReportListener;
import com.bobjamin.kratosplugin.models.Metric;
import com.bobjamin.kratosplugin.services.CodeAnalysisService;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.Gray;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;

public class KratosToolWindow implements CodeReportListener {

    private JPanel content;

    // Header
    private JPanel header;
    private JPanel headerWrapper;
    private JLabel filename;
    private JLabel score;

    // Body
    private JPanel body;
    private JScrollPane metricScrollPane;
    private JPanel metricsWrapper;
    private JPanel metricsContainer;

    // Footer
    private JPanel footer;
    private JLabel footerLabel;
    private JProgressBar progressBar;

    public KratosToolWindow(ToolWindow toolWindow) {
        CodeAnalysisService codeAnalysisService = ApplicationManager.getApplication().getService(CodeAnalysisService.class);
        codeAnalysisService.subscribe(this);
        initializeMetricContainer();
        configureStyles();
    }

    public JPanel getContent() {
        return content;
    }

    @Override
    public void update(CodeReport codeReport) {
        filename.setText(codeReport.getFilename());
        // metricsContainer.removeAll();
        addMetric(new Metric("Weighted Method Count", codeReport.getWmc()));
        addMetric(new Metric("Weighted Method Count", codeReport.getWmc()));
        addMetric(new Metric("Weighted Method Count", codeReport.getWmc()));
        addMetric(new Metric("Weighted Method Count", codeReport.getWmc()));
        // currentDate.setText(String.format("WMC: %s\nTCC: %s\nATFD: %s", codeReport.getWmc(), codeReport.getTcc(), codeReport.getAtfd()));
    }

    private void configureStyles() {
        // Header
        content.setBorder(JBUI.Borders.customLineTop(Gray._53));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.GREEN));
        headerWrapper.setBorder(JBUI.Borders.empty(5));
        filename.setFont(filename.getFont().deriveFont(filename.getFont().getStyle() | Font.BOLD));
        score.setFont(score.getFont().deriveFont(score.getFont().getStyle() | Font.BOLD));

        // Body
        // metricsContainer.setBackground(Gray._30);
        metricsWrapper.setBackground(Color.red);
        metricsContainer.setBackground(Color.BLUE);
        // Footer
        footer.setBorder(JBUI.Borders.empty(5));
    }

    private void initializeMetricContainer() {
        JPanel metricsContainer = new JPanel();
        metricsContainer.setLayout(new BoxLayout(metricsContainer, BoxLayout.Y_AXIS));
        metricsWrapper.add(metricsContainer, BorderLayout.CENTER);
        this.metricsContainer = metricsContainer;
        addMetric(new Metric("Tight Class Cohesion", 200));
    }

    private void addMetric(Metric metric) {
        metricsContainer.add(new KratosToolWindowMetric(metric.getMetricName(), metric.getMetricValue()).getContent());
    }
}
