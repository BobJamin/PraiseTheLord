package com.bobjamin.kratosplugin;

import com.intellij.openapi.ui.DialogWrapper;

import javax.swing.*;
import java.awt.*;

public class AnalyzerDialog extends DialogWrapper {

    protected final String mainText;

    public AnalyzerDialog(String mainText) {
        super(true); // use current window as parent
        this.mainText = mainText;
        setTitle("Kratos");
        init();
    }

    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel(mainText);
        label.setPreferredSize(new Dimension(200, 50));
        dialogPanel.add(label, BorderLayout.CENTER);

        return dialogPanel;
    }
}
