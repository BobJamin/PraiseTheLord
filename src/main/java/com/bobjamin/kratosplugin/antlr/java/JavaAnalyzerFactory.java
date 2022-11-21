package com.bobjamin.kratosplugin.antlr.java;

import com.bobjamin.kratosplugin.antlr.AnalyzerAbstractFactory;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.TokenStream;

public class JavaAnalyzerFactory implements AnalyzerAbstractFactory {
    @Override
    public JavaLexer createLexer(CharStream code) {
        return new JavaLexer(code);
    }

    @Override
    public JavaParser createParser(TokenStream tokens) {
        return new JavaParser(tokens);
    }
}
