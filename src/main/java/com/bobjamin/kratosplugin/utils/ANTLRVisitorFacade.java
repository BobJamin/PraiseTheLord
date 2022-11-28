package com.bobjamin.kratosplugin.utils;

import com.bobjamin.kratosplugin.antlr.common.AnalyzerAbstractFactory;
import com.bobjamin.kratosplugin.antlr.common.AnalyzerFactoryGenerator;
import com.bobjamin.kratosplugin.antlr.common.ExpressionVisitor;
import com.bobjamin.kratosplugin.exceptions.LanguageNotSupportedException;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class ANTLRVisitorFacade {

    public static ExpressionVisitor getExpressionVisitor(String language, String content) throws LanguageNotSupportedException {
        AnalyzerAbstractFactory factory = AnalyzerFactoryGenerator.getFactory(language);
        Lexer lexer = factory.createLexer(CharStreams.fromString(content));
        TokenStream tokens = new CommonTokenStream(lexer);
        ParseTree parseTree = factory.createParseTree(tokens);
        ParseTreeWalker walker = new ParseTreeWalker();
        return factory.createVisitor(parseTree, walker);
    }
}
