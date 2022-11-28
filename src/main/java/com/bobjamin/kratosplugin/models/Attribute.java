package com.bobjamin.kratosplugin.models;

import java.util.HashSet;
import java.util.Set;

public class Attribute {
    private String name;

    private String type;
    private Set<String> calledFrom = new HashSet<>();

    public Attribute(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getAttributeName() {
        return this.name;
    }

    public void addCalledFrom(String caller) {
        calledFrom.add(caller);
    }

    public String getType() {
        return this.type;
    }
}