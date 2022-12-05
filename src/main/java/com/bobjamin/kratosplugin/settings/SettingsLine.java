package com.bobjamin.kratosplugin.settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.function.Predicate;

public class SettingsLine extends JPanel {
    private final SettingsEntry annotation;
    private final SettingsConverter<?> converter;
    private final Field field;
    private JLabel label;
    private JTextField textField;
    private final Settings settings;

    public SettingsLine(Field field, Settings settings) {
        if(field == null)
            throw new IllegalArgumentException("Field cannot be null");

        if (!field.isAnnotationPresent(SettingsEntry.class))
            throw new IllegalArgumentException("Field must be annotated with @SettingsEntry");

        setLayout(new BorderLayout());

        this.field = field;
        this.settings = settings;

        this.annotation = field.getAnnotation(SettingsEntry.class);
        this.converter = getConverter(this.annotation.converter());

        createLabel();
        createTextField();

        add(this.label, BorderLayout.WEST);
        add(this.textField, BorderLayout.EAST);
    }

    private void createLabel() {
        label = new JLabel(annotation.name());
        label.setToolTipText(annotation.description());
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
    }

    private String getFieldValue() {
        try {
            Class<?> classType = converter.getType();
            Object objectValue = settings.getFieldValue(field);

            if (classType.isInstance(objectValue)) {
                return converter.toString(objectValue);
            }

            return annotation.defaultValue();

        }
        catch (IllegalAccessException e) {
            return "";
        }
    }

    private void createTextField() {
        textField = new JTextField(getFieldValue());
        textField.setPreferredSize(new Dimension(100, 20));
        setTextFieldListener(converter::isValid);
    }

    private SettingsConverter<?> getConverter(Class<? extends SettingsConverter<?>> converterClass) {
        try {
            return converterClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("Converter class must have a default constructor");
        }
    }

    private void setTextFieldListener(Predicate<String> predicate) {
        textField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                setError(!predicate.test(textField.getText()));
            }
        });
    }

    private void setError(boolean error) {
        Color color = error ? Color.RED : Color.WHITE;
        textField.setForeground(color);
    }

    public void apply(Settings settings) {
        if(!converter.isValid(textField.getText()))
            return;

        try {
            settings.setFieldValue(field, converter.convert(textField.getText()));
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
