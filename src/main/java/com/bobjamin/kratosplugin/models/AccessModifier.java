package com.bobjamin.kratosplugin.models;

public enum AccessModifier {
    PUBLIC,
    PRIVATE,
    PROTECTED,
    NONE;

    public static boolean contains(String accessModifier) {
        for (AccessModifier c : AccessModifier.values()) {
            if (c.name().equals(accessModifier)) {
                return true;
            }
        }
        return false;
    }
}
