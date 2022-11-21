package com.bobjamin.kratosplugin.antlr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.TokenStream;

public interface AnalyzerAbstractFactory {
    Lexer createLexer(CharStream code);
    Parser createParser(TokenStream tokens);
}
