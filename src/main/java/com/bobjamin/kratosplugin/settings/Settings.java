package com.bobjamin.kratosplugin.settings;

import java.io.Serializable;
import java.lang.reflect.Field;

public abstract class Settings implements Serializable {
    public Field[] getFields() {
        return this.getClass().getDeclaredFields();
    }

    public <T> void setFieldValue(Field field, T value) throws IllegalAccessException {
        boolean isAccessible = field.canAccess(this);
        if(!isAccessible)
            field.setAccessible(true);

        field.set(this, value);

        if(!isAccessible)
            field.setAccessible(false);
    }

    public Object getFieldValue(Field field) throws IllegalAccessException {
        boolean isAccessible = field.canAccess(this);
        if(!isAccessible)
            field.setAccessible(true);

        Object value = field.get(this);

        if(!isAccessible)
            field.setAccessible(false);

        return value;
    }

    public void update() { }

    public Serializable getSerializable() {
        return this;
    }
}
