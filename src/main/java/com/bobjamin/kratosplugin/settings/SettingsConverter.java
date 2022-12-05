package com.bobjamin.kratosplugin.settings;

import org.jetbrains.annotations.NotNull;

public interface SettingsConverter<T> {
    T convert(String value);
    String toString(Object value);
    boolean isValid(String value);
    default @NotNull Class<T> getType() {
        return (Class<T>) Object.class;
    }
}
