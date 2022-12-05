package com.bobjamin.kratosplugin.settings;

public class DoubleConverter implements SettingsConverter<Double> {
    @Override
    public Double convert(String value) {
        return Double.parseDouble(value);
    }

    @Override
    public String toString(Object value) {
        return value.toString();
    }

    @Override
    public boolean isValid(String value) {
        if (value.isBlank())
            return true;

        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
