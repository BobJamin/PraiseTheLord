package com.bobjamin.kratosplugin.ui;

import com.bobjamin.kratosplugin.models.CodeReport;
import com.bobjamin.kratosplugin.models.Metric;
import com.bobjamin.kratosplugin.utils.ColorUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.List;

public class KratosToolWindowClasses {
    private JTree tree1;
    private JPanel panel1;

    public KratosToolWindowClasses(List<CodeReport> codeReports) {
        setStyle();

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Classes");
        for (CodeReport codeReport : codeReports) {
            DefaultMutableTreeNode file = new DefaultMutableTreeNode(codeReport);

            for(Metric metric : codeReport.getMetrics()) {
                DefaultMutableTreeNode metricNode = new DefaultMutableTreeNode(new KratosToolWindowMetric(metric.getMetricName(), metric.getMetricValue()));
                metricNode.setAllowsChildren(false);
                file.add(metricNode);
            }

            root.add(file);
        }

        tree1.setModel(new javax.swing.tree.DefaultTreeModel(root));
    }

    private void setStyle() {
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

                if (leaf) { // Only metrics are pure leaves
                   KratosToolWindowMetric metric = (KratosToolWindowMetric) ((DefaultMutableTreeNode) value).getUserObject();
                    return createDataPanel(metric.getMetricName(), metric.getMetricValue());
                }
                else { // Class...
                    if(((DefaultMutableTreeNode) value).getUserObject() instanceof CodeReport) {
                        CodeReport codeReport = (CodeReport) ((DefaultMutableTreeNode) value).getUserObject();
                        JPanel panel = createDataPanel(codeReport.getClassName(), "" + codeReport.getScore());
                        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, ColorUtil.generateScoreColor(codeReport.getScore())));
                        return panel;
                    }
                }
                return c;
            }
        };

        renderer.setLeafIcon(null);
        renderer.setClosedIcon(null);
        renderer.setOpenIcon(null);
        tree1.setRootVisible(false);
        tree1.setCellRenderer(renderer);
    }

    private JPanel createDataPanel(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setLayout(new BorderLayout());
        JLabel nameLabel = new JLabel(label);
        JLabel valueLabel = new JLabel(cutValue(value));
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(nameLabel, BorderLayout.WEST);
        panel.add(valueLabel, BorderLayout.EAST);

        return panel;
    }

    private String cutValue(String value) {
        String[] digitalValues = value.split("\\.");
        return (digitalValues.length > 1) ?
                digitalValues[0] + '.' + digitalValues[1].substring(0, Math.min(2, digitalValues[1].length())) :
                digitalValues[0];
    }

    public JPanel getContent() {
        return panel1;
    }
}
