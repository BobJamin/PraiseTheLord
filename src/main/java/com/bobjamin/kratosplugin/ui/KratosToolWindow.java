package com.bobjamin.kratosplugin.ui;

import com.bobjamin.kratosplugin.ErrorDialog;
import com.bobjamin.kratosplugin.settings.*;
import com.bobjamin.kratosplugin.models.CodeReport;
import com.bobjamin.kratosplugin.models.CodeReportListener;
import com.bobjamin.kratosplugin.models.Metric;
import com.bobjamin.kratosplugin.services.CodeAnalysisService;
import com.bobjamin.kratosplugin.utils.ColorUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.ui.Gray;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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
    private JButton settingsButton;

    private final Settings settings;

    public KratosToolWindow(Settings settings) {
        CodeAnalysisService codeAnalysisService = ApplicationManager.getApplication().getService(CodeAnalysisService.class);
        codeAnalysisService.subscribe(this);
        this.settings = settings;
        setup();
        style();
    }

    public JPanel getContent() {
        return content;
    }

    public void openSettingsDialog() {
        ApplicationManager.getApplication().invokeLater(() -> {
            SettingsDialog settingsDialog = new SettingsDialog(this.settings);
            settingsDialog.show();
        });
    }

    @Override
    public void displayError(String message) {
        ApplicationManager.getApplication().invokeLater(() -> {
            ErrorDialog dialog = new ErrorDialog(message);
            dialog.show();
        });
    }

    @Override
    public void update(List<CodeReport> codeReports) {
        if(codeReports == null || codeReports.size() == 0)
            return;

        if(codeReports.size() == 1)
            updateWithSingleClass(codeReports.get(0));
        else
            updateWithMultipleClasses(codeReports);
    }

    private void updateWithMultipleClasses(List<CodeReport> codeReports) {
        filename.setText("Multiple classes selected");
        score.setText("");
        score.setVisible(false);

        metricsWrapper.remove(this.metricsContainer);
        metricsWrapper.add(this.metricsContainer, BorderLayout.CENTER);
        metricsContainer.removeAll();
        metricsContainer.add(new KratosToolWindowClasses(codeReports).getContent());
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, ColorUtil.generateScoreColor(0)));
    }

    private void updateWithSingleClass(CodeReport codeReport) {
        filename.setText(codeReport.getClassName());
        score.setText(String.valueOf(codeReport.getScore()));
        score.setVisible(true);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, ColorUtil.generateScoreColor(codeReport.getScore())));
        metricsWrapper.remove(this.metricsContainer);
        metricsWrapper.add(this.metricsContainer, BorderLayout.NORTH);
        metricsContainer.removeAll();
        for (Metric m : codeReport.getMetrics()) {
            addMetric(m.getMetricName(), m.getMetricValue());
        }
    }

    private void setup() {
        // Buttons
        settingsButton.addActionListener(e -> openSettingsDialog());

        // Setup metrics container
        JPanel metricsContainer = new JPanel();
        metricsContainer.setLayout(new BoxLayout(metricsContainer, BoxLayout.Y_AXIS));
        metricsContainer.setBorder(BorderFactory.createEmptyBorder());
        metricsWrapper.add(metricsContainer, BorderLayout.CENTER);
        this.metricsContainer = metricsContainer;

        // Setup default values
        this.filename.setText("No Analysis");
        this.score.setVisible(false);
        this.footer.setVisible(false);
    }

    private void addMetric(String name, double value) {
        metricsContainer.add(new KratosToolWindowMetric(name, value).getContent());
    }

    private void style() {
        styleHeader();
        styleBody();
        styleFooter();
    }

    private void styleHeader() {
        content.setBorder(JBUI.Borders.customLineTop(Gray._53));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0,  ColorUtil.generateScoreColor(120)));
        headerWrapper.setBorder(JBUI.Borders.empty(5));
        filename.setFont(filename.getFont().deriveFont(filename.getFont().getStyle() | Font.BOLD));
        score.setFont(score.getFont().deriveFont(score.getFont().getStyle() | Font.BOLD));
    }

    private void styleBody() {
        body.setBorder(BorderFactory.createEmptyBorder());
        metricScrollPane.setBorder(BorderFactory.createEmptyBorder());
        metricsContainer.setBorder(BorderFactory.createEmptyBorder());
        metricsContainer.setBackground(Gray._43);
    }

    private void styleFooter() {
        footer.setBorder(JBUI.Borders.empty(5));
    }
}
