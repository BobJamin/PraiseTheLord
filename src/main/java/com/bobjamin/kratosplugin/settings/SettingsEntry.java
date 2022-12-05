package com.bobjamin.kratosplugin.settings;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SettingsEntry {
    String name();
    String description();
    String maxValue() default "";
    String minValue() default "";
    String defaultValue() default "";
    Class<? extends SettingsConverter<?>> converter();
}
