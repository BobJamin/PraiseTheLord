package com.bobjamin.kratosplugin.utils;

import com.bobjamin.kratosplugin.models.Class;
import com.bobjamin.kratosplugin.models.CodeReport;
import com.bobjamin.kratosplugin.models.KratosMetrics;
import com.bobjamin.kratosplugin.models.Metric;
import com.bobjamin.kratosplugin.settings.Settings;

import java.util.ArrayList;
import java.util.List;

public class CodeReportBuilder {

    private final Class c;
    private final List<Metric> metrics = new ArrayList<>();
    private final Settings settings;

    private CodeReportBuilder(Class c, Settings settings) {
        this.c = c;
        this.settings = settings;
    }

    public static CodeReportBuilder create(Class c, Settings settings) {
        return new CodeReportBuilder(c, settings);
    }

    public CodeReportBuilder withWMC() {
        double wmcValue = MetricCalculator.computeWMC(c);
        boolean exceedsTreshold = wmcValue > this.settings.getWmcThreshold();
        metrics.add(new Metric(KratosMetrics.WMC.getName(), wmcValue, exceedsTreshold));
        return this;
    }

    public CodeReportBuilder withTCC() {
        double tccValue = MetricCalculator.computeTCC(c);
        boolean exceedsTreshold = tccValue < this.settings.getTccThreshold();
        metrics.add(new Metric(KratosMetrics.TCC.getName(), tccValue, exceedsTreshold));
        return this;
    }

    public CodeReportBuilder withATFD() {
        double tccValue = MetricCalculator.computeATFD(c);
        boolean exceedsTreshold = tccValue > this.settings.getAtfdThreshold();
        metrics.add(new Metric(KratosMetrics.ATFD.getName(), tccValue, exceedsTreshold));
        return this;
    }

    public CodeReport build() {
        return new CodeReport(c.getClassName(), metrics, MetricCalculator.computeTotal(metrics));
    }
}
