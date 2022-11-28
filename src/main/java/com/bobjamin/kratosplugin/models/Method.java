package com.bobjamin.kratosplugin.models;

import java.util.*;

public class Method {
    private String name;
    private AccessModifier accessModifier;
    private final Map<String, Attribute> variables = new HashMap<>();

    private final Map<String, Attribute> parameters = new HashMap<>();

    private final Set<String> attributeAccesses = new HashSet<>();

    private int jumps = 1;

    public Method(String name, AccessModifier accessModifier) {
        this.name = name;
        this.accessModifier = accessModifier;
    }

    public void incrementJumps() {
        this.jumps++;
    }

    public String getMethodName() {
        return this.name;
    }

    public void addAttributeAccesses(String attributeName){
        attributeAccesses.add(attributeName);
    }

    public Set<String> getAttributeAccesses() {
        return attributeAccesses;
    }
    public int getJumps() {
        return jumps;
    }

    public void addParameter(Attribute parameter){
        parameters.put(parameter.getAttributeName(), parameter);
    }

    public Attribute getParameter(String parameterName) {
        return parameters.get(parameterName);
    }

    public Attribute getVariable(String variableName) {
        return variables.get(variableName);
    }

    public void addVariable(Attribute variable){
        variables.put(variable.getAttributeName(), variable);
    }
}