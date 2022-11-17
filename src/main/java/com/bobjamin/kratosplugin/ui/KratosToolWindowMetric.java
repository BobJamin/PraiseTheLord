package com.bobjamin.kratosplugin.ui;

import com.intellij.ui.Gray;
import com.intellij.util.ui.JBUI;

import javax.swing.*;

public class KratosToolWindowMetric {
    private JPanel metricWrapper;
    private JPanel metric;
    private JLabel metricName;
    private JLabel metricScore;

    public KratosToolWindowMetric(String metricLabel, double metricValue) {
        this.metricName.setText(metricLabel);
        this.metricScore.setText(String.valueOf(metricValue));
        style();
    }

    public JPanel getContent() {
        return metric;
    }


    private void style() {
        metricWrapper.setBackground(Gray._43);
        metricWrapper.setBorder(JBUI.Borders.empty(10));
    }
}
