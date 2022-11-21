package com.bobjamin.kratosplugin;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class ErrorDialog extends DialogWrapper {
    private final String mainText;

    public ErrorDialog(String mainText) {
        super(true); // use current window as parent
        this.mainText = mainText;
        setTitle("Kratos");
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel(mainText);
        label.setPreferredSize(new Dimension(200, 50));
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        dialogPanel.add(label, BorderLayout.CENTER);

        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(UIManager.getIcon("OptionPane.errorIcon"));
        dialogPanel.add(iconLabel, BorderLayout.WEST);

        return dialogPanel;
    }
}
