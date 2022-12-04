package com.bobjamin.kratosplugin.settings;

public class KratosConfigurator {
    @SettingsEntry(name = "Max summed up score", description = "Maximum summed up score for a class", maxValue = "100", minValue = "0", defaultValue = "100", setter = "setMaxScore", predicate = "isDouble")
    private double maxScore = 0.0;
    @SettingsEntry(name = "Tight class cohesion", description = "Tight Class Cohesion threshold", maxValue = "100", minValue = "0", defaultValue = "33.3333", setter = "setTcc", predicate = "isDouble")
    private double tcc = 33.3;
    @SettingsEntry(name = "Weighted methods per class", description = "Weighted Methods per Class threshold", minValue = "0", defaultValue = "33.3333", setter = "setWmc", predicate = "isDouble")
    private double wmc = 20;
    @SettingsEntry(name = "Access to foreign data", description = "Access to Foreign Data threshold", minValue = "0", defaultValue = "33.3333", setter = "setAtfd", predicate = "isDouble")
    private double atfd = 20;

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

    public void setMaxScore(String maxScore) {
        this.maxScore = Converter.convertToDouble(maxScore);
    }

    public void setTcc(String tcc) {
        this.tcc = Converter.convertToDouble(tcc);
    }

    public void setWmc(String wmc) {
        this.wmc = Converter.convertToDouble(wmc);
    }

    public void setAtfd(String atfd) {
        this.atfd = Converter.convertToDouble(atfd);
    }

    public boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void save() {
        System.out.println("Saving settings");
    }
}
