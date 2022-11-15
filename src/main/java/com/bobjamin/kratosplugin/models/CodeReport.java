package com.bobjamin.kratosplugin.models;

public class CodeReport {

    public String getFilename() {
        return filename;
    }

    private final String filename;
    private double wmc;
    private double tcc;
    private int atfd;

    public CodeReport(String filename, double wmc, double tcc, int atfd) {
        this.filename = filename;
        this.wmc = wmc;
        this.tcc = tcc;
        this.atfd = atfd;
    }

    public double getWmc() {
        return wmc;
    }

    public double getTcc() {
        return tcc;
    }

    public int getAtfd() {
        return atfd;
    }
}
