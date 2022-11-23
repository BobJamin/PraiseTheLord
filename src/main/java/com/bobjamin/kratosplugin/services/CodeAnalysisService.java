package com.bobjamin.kratosplugin.services;

import com.bobjamin.kratosplugin.antlr.AnalyzedLanguage;
import com.bobjamin.kratosplugin.antlr.AnalyzerAbstractFactory;
import com.bobjamin.kratosplugin.antlr.ExpressionsVisitor;
import com.bobjamin.kratosplugin.antlr.java.JavaLexer;
import com.bobjamin.kratosplugin.antlr.java.JavaParser;
import com.bobjamin.kratosplugin.antlr.java.JavaParserBaseListener;
import com.bobjamin.kratosplugin.models.CodeReport;
import com.bobjamin.kratosplugin.models.CodeReportListener;
import com.bobjamin.kratosplugin.models.Metric;
import com.intellij.lang.Language;

import java.util.ArrayList;
import java.util.Collection;
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
            pushError("Language not supported: " + language.getDisplayName());
            return;
        }

        AnalyzerAbstractFactory factory = analyzedLanguage.getAnalyzerFactory();
        if(factory == null) {
            pushError("Classpath corruption");
            return;
        }

        Lexer lexer = factory.createLexer(CharStreams.fromString(text));
        TokenStream tokens = new CommonTokenStream(lexer);
        ParseTree parseTree = factory.createParseTree(tokens);

        ParseTreeWalker walker = new ParseTreeWalker();
        ExpressionsVisitor visitor = factory.createVisitor(parseTree, walker);

        Collection<String> classes = visitor.getClasses();
        String firstClass = classes.iterator().next();
        double atfd = visitor.getAtfd(firstClass);
        double tcc = visitor.getTcc(firstClass);
        double wmc = visitor.getWmc(firstClass);

        List<Metric> metrics = List.of(
                new Metric(WMC_METRIC_NAME, wmc),
                new Metric(TCC_METRIC_NAME, tcc),
                new Metric(ATFD_METRIC_NAME, atfd),
                new Metric("Total Score", 10));

        this.codeReport = new CodeReport(filename, metrics, 10);
        notifyListeners();
    }

    private void pushError(String message) {
        for(CodeReportListener listener : listeners) {
            listener.displayError(message);
        }
    }

    private void notifyListeners(){
        for (CodeReportListener l: listeners) {
            l.update(codeReport);
        }
    }
}
