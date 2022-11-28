package com.bobjamin.kratosplugin.antlr.common;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public interface AnalyzerAbstractFactory {
    Lexer createLexer(CharStream code);
    ParseTree createParseTree(TokenStream tokens);
    ExpressionVisitor createVisitor(ParseTree parseTree, ParseTreeWalker walker);
}
