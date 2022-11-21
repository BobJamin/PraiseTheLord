package com.bobjamin.kratosplugin.antlr;

public enum AnalyzedLanguage {
    JAVA("java", "com.bobjamin.kratosplugin.antlr.java.JavaAnalyzerFactory");

    private final String language;
    private final String factory;

    AnalyzedLanguage(String language, String factory) {
        this.language = language;
        this.factory = factory;
    }

    public String getLanguage() {
        return language;
    }

    public AnalyzerAbstractFactory getAnalyzerFactory() {
        try {
            return (AnalyzerAbstractFactory) Class.forName(factory).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static AnalyzedLanguage fromName(String language) {
        for (AnalyzedLanguage l : AnalyzedLanguage.values())
            if (l.getLanguage().equals(language.toLowerCase()))
                return l;

        return null;
    }
}
