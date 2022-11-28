package com.bobjamin.kratosplugin.models;

public enum KratosMetrics {
    WMC("Weighted Method Count"),
    TCC("Tight Class Cohesion"),
    ATFD("Access To Foreign Data");

    private final String name;

    KratosMetrics(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
