package com.bobjamin.kratosplugin.antlr;

import com.bobjamin.kratosplugin.models.Metric;
import org.antlr.v4.runtime.tree.ParseTreeListener;

import java.util.Collection;
import java.util.List;

public interface ExpressionsVisitor extends ParseTreeListener {
    double getAtfd(String className);
    double getTcc(String className);
    double getWmc(String className);
    Collection<String> getClasses();
}
