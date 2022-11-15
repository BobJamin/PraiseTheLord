package com.bobjamin.kratosplugin.ui;

import com.intellij.ui.Gray;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;

public class KratosToolWindowMetric {
    private JPanel metricPanelWrapper;
    private JPanel metricPanel;
    private JLabel metricLabel;
    private JLabel metricValue;

    public KratosToolWindowMetric(String metricLabel, double metricValue) {
        this.metricLabel.setText(metricLabel);
        this.metricValue.setText(String.valueOf(metricValue));
        configureStyles();
    }

    public JPanel getContent() {
        return metricPanel;
    }


    private void configureStyles() {
        // Header
        // metricPanel.setBackground(Color.blue);
        metricPanel.setBorder(JBUI.Borders.empty(10));
        metricPanelWrapper.setBorder(JBUI.Borders.customLineBottom(Gray._53));
    }
}
