package com.bobjamin.kratosplugin.antlr;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;

public interface AnalyzerFactory {
    Lexer createLexer();
    Parser createParser();
}
