package com.bobjamin.kratosplugin.settings;

import com.bobjamin.kratosplugin.settings.KratosConfigurator;
import com.bobjamin.kratosplugin.settings.SettingsEntry;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.Arrays;

public class SettingsDialog extends DialogWrapper {
    private final KratosConfigurator configurator;
    private final SettingsLine[] options;
    public SettingsDialog(KratosConfigurator configurator) {
        super(true); // use current window as parent
        this.configurator = configurator;
        this.options = Arrays.stream(configurator.getClass().getDeclaredFields()).
                filter(field -> field.isAnnotationPresent(SettingsEntry.class)).
                map(SettingsLine::new).
                toArray(SettingsLine[]::new);

        setTitle("Kratos Settings");
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        // Parse fields annotated with @SettingsEntry in KratosConfigurator
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
            option.apply(configurator);
        }

        this.configurator.save();
        super.doOKAction();
    }
}
