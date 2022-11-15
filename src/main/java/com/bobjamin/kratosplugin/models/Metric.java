package com.bobjamin.kratosplugin.models;

public class Metric {

    private String metricName;
    private double metricValue;

    public Metric(String metricName, double metricValue) {
        this.metricName = metricName;
        this.metricValue = metricValue;
    }

    public String getMetricName() {
        return metricName;
    }

    public double getMetricValue() {
        return metricValue;
    }

}
