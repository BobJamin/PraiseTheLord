package com.bobjamin.kratosplugin.services;

import com.bobjamin.kratosplugin.antlr.AnalyzedLanguage;
import com.bobjamin.kratosplugin.antlr.AnalyzerAbstractFactory;
import com.bobjamin.kratosplugin.antlr.java.JavaLexer;
import com.bobjamin.kratosplugin.antlr.java.JavaParser;
import com.bobjamin.kratosplugin.models.CodeReport;
import com.bobjamin.kratosplugin.models.CodeReportListener;
import com.bobjamin.kratosplugin.models.Metric;
import com.intellij.lang.Language;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
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
        AnalyzedLanguage analyzedLanguage = AnalyzedLanguage.fromName(language.getDisplayName());
        if(analyzedLanguage == null) {
            for(CodeReportListener listener : listeners) {
                listener.displayError("Language not supported: " + language.getDisplayName());
            }
            return;
        }

        AnalyzerAbstractFactory factory = analyzedLanguage.getAnalyzerFactory();
        Lexer lexer = factory.createLexer(CharStreams.fromString(text));
        TokenStream tokens = new CommonTokenStream(lexer);
        Parser parser = new JavaParser(tokens);

        //ParseTree tree = parser.compilationUnit();
        ParseTreeWalker walker = new ParseTreeWalker();

        List<Metric> metrics = List.of(
                new Metric(WMC_METRIC_NAME, 18),
                new Metric(TCC_METRIC_NAME, 0.1),
                new Metric(ATFD_METRIC_NAME, 8),
                new Metric("Total Score", 10));

        this.codeReport = new CodeReport(filename, metrics, 10);
        notifyListeners();
    }

    private void notifyListeners(){
        for (CodeReportListener l: listeners) {
            l.update(codeReport);
        }
    }
}
