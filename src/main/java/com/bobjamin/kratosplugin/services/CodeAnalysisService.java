package com.bobjamin.kratosplugin.services;

import com.bobjamin.kratosplugin.exceptions.LanguageNotSupportedException;
import com.bobjamin.kratosplugin.models.Class;
import com.bobjamin.kratosplugin.antlr.common.ExpressionVisitor;
import com.bobjamin.kratosplugin.models.CodeReport;
import com.bobjamin.kratosplugin.models.CodeReportListener;
import com.bobjamin.kratosplugin.utils.ANTLRVisitorFacade;
import com.bobjamin.kratosplugin.utils.CodeReportBuilder;
import com.intellij.lang.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CodeAnalysisService {

    private final List<CodeReportListener> listeners = new ArrayList<>();
    private List<CodeReport> codeReports;

    public void subscribe(CodeReportListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(CodeReportListener listener) {
        listeners.remove(listener);
    }

    public void run(String filename, Language language, String text) {
        try {
            ExpressionVisitor visitor = ANTLRVisitorFacade.getExpressionVisitor(language.getDisplayName(), text);
            codeReports = visitor.getClasses().stream().map(this::buildCodeReport).collect(Collectors.toList());
            notifyListeners();
        } catch (LanguageNotSupportedException ex) {
            pushError("Language not supported: " + language.getDisplayName());
        }
    }

    public void run(String[] filenames, Language[] languages, String[] texts) {
        if (filenames.length != languages.length || filenames.length != texts.length) {
            pushError("Invalid input");
            return;
        }

        codeReports = new ArrayList<>();
        for (int i = 0; i < filenames.length; i++) {
            try {
                ExpressionVisitor visitor = ANTLRVisitorFacade.getExpressionVisitor(languages[i].getDisplayName(), texts[i]);
                codeReports.add(buildCodeReport(visitor.getClasses().get(0)));
            } catch (LanguageNotSupportedException ex) {
                pushError("Language not supported: " + languages[i].getDisplayName());
            }
        }
        notifyListeners();
    }

    private CodeReport buildCodeReport(Class c) {
        return CodeReportBuilder.Create(c).WithWMC().WithTCC().WithATFD().Build();
    }

    private void pushError(String message) {
        for(CodeReportListener listener : listeners) {
            listener.displayError(message);
        }
    }

    private void notifyListeners(){
        for (CodeReportListener l: listeners) {
            l.update(codeReports);
        }
    }
}
