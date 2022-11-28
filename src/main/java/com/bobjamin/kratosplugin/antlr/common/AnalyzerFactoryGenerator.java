package com.bobjamin.kratosplugin.antlr.common;

import com.bobjamin.kratosplugin.exceptions.LanguageNotSupportedException;

import java.util.HashMap;
import java.util.Map;

public class AnalyzerFactoryGenerator {

    private static final Map<String, String> factoryMap = new HashMap<>() {{
        put("Java", "com.bobjamin.kratosplugin.antlr.java.JavaAnalyzerFactory");
    }};

    public static AnalyzerAbstractFactory getFactory(String language) throws LanguageNotSupportedException {
        try {
            return (AnalyzerAbstractFactory) java.lang.Class.forName(factoryMap.get(language)).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new LanguageNotSupportedException(String.format("Language %s is not currently supported by Kratos", language));
        }
    }
}
