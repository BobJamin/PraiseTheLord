package com.bobjamin.kratosplugin.settings;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;
import javax.swing.*;
import java.util.Arrays;

public class SettingsDialog extends DialogWrapper {
    private final Settings settings;
    private final SettingsLine[] options;

    public SettingsDialog(Settings settings) {
        super(true);
        this.settings = settings;
        this.options = Arrays.stream(settings.getFields()).
                filter(field -> field.isAnnotationPresent(SettingsEntry.class)).
                map((f) -> new SettingsLine(f, this.settings)).
                toArray(SettingsLine[]::new);

        setTitle("Kratos Settings");
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (SettingsLine option : options) {
            panel.add(option);
        }

        return panel;
    }

    @Override
    protected void doOKAction() {
        for (SettingsLine option : options) {
            option.apply(settings);
        }

        this.settings.update();
        super.doOKAction();
    }
}
