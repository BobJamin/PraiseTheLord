package com.bobjamin.kratosplugin.utils;

import com.bobjamin.kratosplugin.models.Class;
import com.bobjamin.kratosplugin.models.CodeReport;
import com.bobjamin.kratosplugin.models.KratosMetrics;
import com.bobjamin.kratosplugin.models.Metric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CodeReportBuilder {

    private final Class c;

    private final List<Metric> metrics = new ArrayList<>();

    private CodeReportBuilder(Class c) {
        this.c = c;
    }

    public static CodeReportBuilder Create(Class c) {
        return new CodeReportBuilder(c);
    }

    public CodeReportBuilder WithWMC()
    {
        metrics.add(new Metric(KratosMetrics.WMC.getName(), MetricCalculator.computeWMC(c)));
        return this;
    }

    public CodeReportBuilder WithTCC()
    {
        metrics.add(new Metric(KratosMetrics.TCC.getName(), MetricCalculator.computeTCC(c)));
        return this;
    }

    public CodeReportBuilder WithATFD()
    {
        metrics.add(new Metric(KratosMetrics.ATFD.getName(), MetricCalculator.computeATFD(c)));
        return this;
    }

    public CodeReport Build()
    {
        return new CodeReport(c.getClassName(), metrics, MetricCalculator.computeTotal(metrics));
    }
}
