package com.bobjamin.kratosplugin.settings;

import java.io.*;
import java.lang.reflect.Field;

public class KratosConfigurator extends Settings {
    @SettingsEntry(name = "Minimum TCC", description = "Tight Class Cohesion threshold", maxValue = "1", minValue = "0", defaultValue = "0.33", converter = DoubleConverter.class)
    private Double tcc = 0.33;
    @SettingsEntry(name = "WMC Threshold", description = "Weighted Methods per Class threshold", minValue = "0", defaultValue = "20", converter = DoubleConverter.class)
    private Double wmc = 20.0;
    @SettingsEntry(name = "ATFD Threshold", description = "Access to Foreign Data threshold", minValue = "0", defaultValue = "20", converter = DoubleConverter.class)
    private Double atfd = 20.0;

    public double getTccThreshold() {
        return tcc;
    }

    public double getWmcThreshold() {
        return wmc;
    }

    public double getAtfdThreshold() {
        return atfd;
    }
}
