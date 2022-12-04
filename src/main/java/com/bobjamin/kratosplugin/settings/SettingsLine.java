package com.bobjamin.kratosplugin.settings;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Predicate;

public class SettingsLine extends JPanel {
    private SettingsEntry annotation;
    private Method setter = null;
    private JTextField textField;
    private String value;

    public SettingsLine(Field field) {
        if(field == null)
            throw new IllegalArgumentException("Field cannot be null");

        if (!field.isAnnotationPresent(SettingsEntry.class))
            throw new IllegalArgumentException("Field must be annotated with @SettingsEntry");

        setLayout(new BorderLayout());

        annotation = field.getAnnotation(SettingsEntry.class);
        JLabel label = new JLabel(annotation.name());
        label.setToolTipText(annotation.description());

        textField = new JTextField(annotation.defaultValue());
        textField.setPreferredSize(new Dimension(100, 20));
        setTextFieldListener((String value) -> {
            try {
                Double.parseDouble(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        });

        add(label, BorderLayout.WEST);
        add(textField, BorderLayout.EAST);

        // --- Find setter amongst methods
        extractSetter(field);
    }

    private void extractSetter(final Field field) {
        for (Class<?> clazz = field.getDeclaringClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                setter = clazz.getDeclaredMethod(annotation.setter(), String.class);
                break;
            }
            catch (NoSuchMethodException ignored) {
            }
        }
    }

    private void setTextFieldListener(Predicate<String> predicate) {
        textField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if (!predicate.test(textField.getText())) {
                    // prevent user from typing invalid characters
                    textField.setText(textField.getText());
                }
            }
        });
    }

    public void apply(KratosConfigurator configurator) {
        if (setter != null) {
            try {
                setter.invoke(configurator, textField.getText());
            }
            catch (Exception ignored) { }
        }
    }
}
