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
        setup();
        style();
    }

    public JPanel getContent() {
        return content;
    }

    @Override
    public void update(CodeReport codeReport) {
        filename.setText(codeReport.getFilename());
        score.setText(String.valueOf(codeReport.getScore()));
        score.setVisible(true);
        metricsContainer.removeAll();
        for (Metric m : codeReport.getMetrics()) {
            addMetric(m);
        }
    }

    private void setup() {
        // Setup metrics container
        JPanel metricsContainer = new JPanel();
        metricsContainer.setLayout(new BoxLayout(metricsContainer, BoxLayout.Y_AXIS));
        metricsContainer.setBorder(BorderFactory.createEmptyBorder());
        metricsWrapper.add(metricsContainer, BorderLayout.NORTH);
        this.metricsContainer = metricsContainer;

        // Setup default values
        this.filename.setText("No Analysis");
        this.score.setVisible(false);
        this.footer.setVisible(false);
    }

    private void style() {
        // Header
        content.setBorder(JBUI.Borders.customLineTop(Gray._53));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.GREEN));
        headerWrapper.setBorder(JBUI.Borders.empty(5));
        filename.setFont(filename.getFont().deriveFont(filename.getFont().getStyle() | Font.BOLD));
        score.setFont(score.getFont().deriveFont(score.getFont().getStyle() | Font.BOLD));

        // Body
        body.setBorder(BorderFactory.createEmptyBorder());
        metricScrollPane.setBorder(BorderFactory.createEmptyBorder());
        metricsContainer.setBorder(BorderFactory.createEmptyBorder());
        metricsContainer.setBackground(Gray._43);

        // Footer
        footer.setBorder(JBUI.Borders.empty(5));
    }

    private void addMetric(Metric metric) {
        metricsContainer.add(new KratosToolWindowMetric(metric.getMetricName(), metric.getMetricValue()).getContent());
    }
}
