package com.bobjamin.kratosplugin.antlr.java;

import com.bobjamin.kratosplugin.antlr.AnalyzerAbstractFactory;
import com.bobjamin.kratosplugin.antlr.ExpressionsVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class JavaAnalyzerFactory implements AnalyzerAbstractFactory {
    @Override
    public JavaLexer createLexer(CharStream code) {
        return new JavaLexer(code);
    }

    @Override
    public ParseTree createParseTree(TokenStream tokens) {
        return new JavaParser(tokens).compilationUnit();
    }

    @Override
    public ExpressionsVisitor createVisitor(ParseTree parseTree, ParseTreeWalker walker) {
        CustomJavaParserListener listener = new CustomJavaParserListener();
        walker.walk(listener, parseTree);
        return listener;
    }
}
