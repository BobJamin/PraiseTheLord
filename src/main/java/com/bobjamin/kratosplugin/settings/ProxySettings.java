package com.bobjamin.kratosplugin.settings;

import java.io.Serializable;
import java.lang.reflect.Field;

public class ProxySettings extends Settings {
    private final KratosConfigurator configurator;
    private final LoaderSaver<KratosConfigurator> loaderSaver;

    public ProxySettings(LoaderSaver<KratosConfigurator> loaderSaver) {
        this.configurator = loaderSaver.loadOrDefault();
        this.loaderSaver = loaderSaver;
    }

    @Override
    public Field[] getFields() {
        return configurator.getFields();
    }

    @Override
    public Object getFieldValue(Field field) throws IllegalAccessException {
        return configurator.getFieldValue(field);
    }

    @Override
    public <T> void setFieldValue(Field field, T value) throws IllegalAccessException {
        this.configurator.setFieldValue(field, value);
    }

    @Override
    public void update() {
        configurator.update();
        loaderSaver.save(configurator);
    }

    @Override
    public Serializable getSerializable() {
        return this.configurator;
    }
}
