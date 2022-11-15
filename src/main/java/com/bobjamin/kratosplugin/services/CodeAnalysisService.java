package com.bobjamin.kratosplugin.services;

import com.bobjamin.kratosplugin.models.CodeReport;
import com.bobjamin.kratosplugin.models.CodeReportListener;
import com.intellij.lang.Language;

import java.util.ArrayList;
import java.util.List;

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
        codeReport = new CodeReport(filename, 24, 0.8, 12);
        notifyListeners();
    }

    private void notifyListeners(){
        for (CodeReportListener l: listeners) {
            l.update(codeReport);
        }
    }
}
