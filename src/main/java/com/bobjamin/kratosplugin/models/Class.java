package com.bobjamin.kratosplugin.models;

import java.util.*;

public class Class {

    private String name;
    private final Map<String, Method> methods = new HashMap<>();
    private final Map<String, Attribute> attributes = new HashMap<>();

    private Set<String> externalAccesses = new HashSet<>();

    public Class(String name){
        this.name = name;
    }

    public String getClassName() {
        return name;
    }

    public Method getMethod(String methodName) {
        return methods.get(methodName);
    }

    public List<Method> getMethods() {
        return new ArrayList<>(methods.values());
    }

    public void addMethod(Method method) {
        methods.put(method.getMethodName(), method);
    }

    public void addAttribute(Attribute attribute) {
        attributes.put(attribute.getAttributeName(), attribute);
    }

    public Attribute getAttribute(String attributeName) {
        return attributes.get(attributeName);
    }

    public void addExternalAccess(String access) {
        externalAccesses.add(access);
    }

    public Set<String> getExternalAccesses() {
        return this.externalAccesses;
    }

}
