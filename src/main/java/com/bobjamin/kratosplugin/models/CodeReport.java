package com.bobjamin.kratosplugin.models;

import java.util.List;

public class CodeReport {

    private final String className;
    private final List<Metric> metrics;
    private final double score;

    public CodeReport(String className, List<Metric> metrics, double score) {
        this.className = className;
        this.metrics = metrics;
        this.score = score;
    }

    public List<Metric> getMetrics() {
        return metrics;
    }

    public double getScore() {
        return score;
    }

    public String getClassName() {
        return className;
    }
}
