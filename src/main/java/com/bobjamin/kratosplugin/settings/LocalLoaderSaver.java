package com.bobjamin.kratosplugin.settings;

import java.io.*;

public class LocalLoaderSaver implements LoaderSaver<KratosConfigurator> {
    private static final String SETTINGS_PATH = System.getProperty("user.home") + "/.kratos/kratos_settings.json";

    @Override
    public KratosConfigurator loadOrDefault() {
        try(FileInputStream fis = new FileInputStream(SETTINGS_PATH)) {
            try(ObjectInputStream ois = new ObjectInputStream(fis)) {
                return (KratosConfigurator) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            return new KratosConfigurator();
        }
    }

    @Override
    public void save(KratosConfigurator object) {
        File file = new File(SETTINGS_PATH);
        file.getParentFile().mkdirs();

        try(FileOutputStream fos = new FileOutputStream(SETTINGS_PATH)) {
            try(ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(object.getSerializable());
            }
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
