package com.bobjamin.kratosplugin.models;

public class Metric {
    private String metricName;
    private double metricValue;
    private boolean exceedsTreshold;

    public Metric(String metricName, double metricValue, boolean exceedsTreshold) {
        this.metricName = metricName;
        this.metricValue = metricValue;
        this.exceedsTreshold = exceedsTreshold;
    }

    public String getMetricName() {
        return metricName;
    }

    public double getMetricValue() {
        return metricValue;
    }

    public boolean isOverTheshold() {
        return exceedsTreshold;
    }

}
