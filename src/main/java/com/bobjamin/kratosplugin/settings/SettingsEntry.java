package com.bobjamin.kratosplugin.settings;

import javax.annotation.Nullable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@Retention(RetentionPolicy.RUNTIME)
public @interface SettingsEntry {
    String name();
    String description();
    String maxValue() default "";
    String minValue();
    String defaultValue();
    String setter();
    String predicate() default "";
}
