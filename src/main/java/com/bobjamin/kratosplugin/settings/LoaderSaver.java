package com.bobjamin.kratosplugin.settings;

public interface LoaderSaver<T> {
    T loadOrDefault();
    void save(T object);
}
