package com.bobjamin.kratosplugin.ui;

import com.intellij.ui.Gray;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;

public class KratosToolWindowMetric extends JTree {
    private JPanel metricWrapper;
    private JPanel metric;
    private JLabel metricName;
    private JLabel metricScore;

    public KratosToolWindowMetric(String metricLabel, double metricValue, boolean isOverTreshold) {
        this.metricName.setText(metricLabel);
        this.metricScore.setText(String.valueOf(metricValue));
        if(isOverTreshold) {
            this.metricName.setForeground(new Color(0xc75450));
            this.metricScore.setForeground(new Color(0xc75450));
        }
        style();
    }

    public JPanel getContent() {
        return metric;
    }

    private void style() {
        metricWrapper.setBackground(Gray._43);
        metricWrapper.setBorder(JBUI.Borders.empty(10));
    }

    public String getMetricName() {
        return metricName.getText();
    }

    public String getMetricValue() {
        return metricScore.getText();
    }
}
