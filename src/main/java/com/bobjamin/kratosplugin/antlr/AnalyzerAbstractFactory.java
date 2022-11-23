package com.bobjamin.kratosplugin.antlr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public interface AnalyzerAbstractFactory {
    Lexer createLexer(CharStream code);
    ParseTree createParseTree(TokenStream tokens);
    ExpressionsVisitor createVisitor(ParseTree parseTree, ParseTreeWalker walker);
}
