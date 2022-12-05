package com.bobjamin.kratosplugin.settings;

import java.io.*;
import java.lang.reflect.Field;

public class KratosConfigurator extends Settings {
    @SettingsEntry(name = "Max summed up score", description = "Maximum summed up score for a class", maxValue = "100", minValue = "0", defaultValue = "100", converter = DoubleConverter.class)
    private Double maxScore = 0.0;
    @SettingsEntry(name = "Tight class cohesion", description = "Tight Class Cohesion threshold", maxValue = "100", minValue = "0", defaultValue = "33.3333", converter = DoubleConverter.class)
    private Double tcc = 33.3;
    @SettingsEntry(name = "Weighted methods per class", description = "Weighted Methods per Class threshold", minValue = "0", defaultValue = "33.3333", converter = DoubleConverter.class)
    private Double wmc = 20.0;
    @SettingsEntry(name = "Access to foreign data", description = "Access to Foreign Data threshold", minValue = "0", defaultValue = "33.3333", converter = DoubleConverter.class)
    private Double atfd = 20.0;

    public double getMaxScore() {
        return maxScore;
    }

    public double getTcc() {
        return tcc;
    }

    public double getWmc() {
        return wmc;
    }

    public double getAtfd() {
        return atfd;
    }
}
