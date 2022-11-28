package com.bobjamin.kratosplugin.antlr.common;

import com.bobjamin.kratosplugin.models.Class;
import org.antlr.v4.runtime.tree.ParseTreeListener;

import java.util.List;

public interface ExpressionVisitor extends ParseTreeListener {
    List<Class> getClasses();
}
