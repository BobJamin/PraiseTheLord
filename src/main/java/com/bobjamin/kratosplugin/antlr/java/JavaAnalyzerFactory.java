package com.bobjamin.kratosplugin.antlr.java;

import com.bobjamin.kratosplugin.antlr.common.AnalyzerAbstractFactory;
import com.bobjamin.kratosplugin.antlr.common.ExpressionVisitor;
import org.antlr.v4.runtime.CharStream;
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
    public ExpressionVisitor createVisitor(ParseTree parseTree, ParseTreeWalker walker) {
        CustomJavaParserListener listener = new CustomJavaParserListener();
        walker.walk(listener, parseTree);
        return listener;
    }
}
