package com.bobjamin.kratosplugin.models;

import java.util.List;

public class CodeReport {

    private final String filename;
    private final List<Metric> metrics;
    private final double score;

    public CodeReport(String filename, List<Metric> metrics, double score) {
        this.filename = filename;
        this.metrics = metrics;
        this.score = score;
    }

    public String getFilename() {
        return filename;
    }

    public List<Metric> getMetrics() {
        return metrics;
    }

    public double getScore() {
        return score;
    }

}
