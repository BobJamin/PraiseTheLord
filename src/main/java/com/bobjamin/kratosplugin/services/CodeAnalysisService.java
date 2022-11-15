package com.bobjamin.kratosplugin.services;

import com.bobjamin.kratosplugin.models.CodeReport;
import com.bobjamin.kratosplugin.models.CodeReportListener;
import com.bobjamin.kratosplugin.models.Metric;
import com.intellij.lang.Language;

import java.util.ArrayList;
import java.util.List;

import static com.bobjamin.kratosplugin.utils.Constants.*;

public class CodeAnalysisService {

    private final List<CodeReportListener> listeners = new ArrayList<>();
    private CodeReport codeReport;

    public void subscribe(CodeReportListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(CodeReportListener listener) {
        listeners.remove(listener);
    }

    // TODO: Allow to launch code analysis on an arborescence;
    public void run(String filename, Language language, String text) {
        List<Metric> metrics = List.of(
                new Metric(WMC_METRIC_NAME, 18),
                new Metric(TCC_METRIC_NAME, 0.1),
                new Metric(ATFD_METRIC_NAME, 8),
                new Metric("Total Score", 89));
        this.codeReport = new CodeReport(filename, metrics, 89);
        notifyListeners();
    }

    private void notifyListeners(){
        for (CodeReportListener l: listeners) {
            l.update(codeReport);
        }
    }
}
