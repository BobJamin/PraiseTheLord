package com.bobjamin.kratosplugin.antlr.java;

import com.bobjamin.kratosplugin.antlr.ExpressionsVisitor;
import org.antlr.v4.runtime.RuleContext;

import java.util.*;

// ref: https://www.simpleorientedarchitecture.com/how-to-identify-god-class-using-ndepend/
public class CustomJavaParserListener extends JavaParserBaseListener implements ExpressionsVisitor {
    private int methods = 0;
    private int attributes = 0;
    private String currentMethod = null;
    private String currentPubliciy = null;
    private Stack<String> classStack = new Stack<>();
    private Map<String, ClassData> classDataMap = new HashMap<>();

    @Override
    public double getAtfd(String className) {
        return methods;
    }

    @Override
    public double getTcc(String className) {
        return 0;
    }

    @Override
    public double getWmc(String className) {
        return classDataMap.containsKey(className) ? classDataMap.get(className).getWmc() : 0;
    }

    @Override
    public Collection<String> getClasses() {
        return classDataMap.keySet();
    }

    private Map<String, ClassData> getClassDataMap() {
        return classDataMap;
    }

    @Override
    public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        String className = ctx.identifier().getText();
        classStack.push(className);

        ClassData classData = new ClassData();
        classData.name = className;
        classDataMap.put(className, classData);
    }

    public void exitClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        classStack.pop();
    }

    @Override
    public void enterSwitchLabel(JavaParser.SwitchLabelContext ctx) {
        super.enterSwitchLabel(ctx);
        incrementJumps();
    }

    @Override
    public void enterCatchClause(JavaParser.CatchClauseContext ctx) {
        super.enterCatchClause(ctx);
        incrementJumps();
    }

    @Override
    public void enterFinallyBlock(JavaParser.FinallyBlockContext ctx) {
        super.enterFinallyBlock(ctx);
        incrementJumps();
    }

    private void incrementJumps() {
        ClassData classData = classDataMap.get(getCurrentClass());
        classData.methodsData.get(currentMethod).jumps++;
    }

    @Override
    public void enterModifier(JavaParser.ModifierContext ctx) {
        super.enterModifier(ctx);
        String[] publicities = {"public", "private", "protected"};
        if (Arrays.asList(publicities).contains(ctx.getText())) {
            currentPubliciy = ctx.getText();
        }
    }

    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        super.enterMethodDeclaration(ctx);

        // Dieu que c'est moche -> TODO: Refactor
        currentMethod = ctx.identifier().getText();
        ClassData classData = classDataMap.get(getCurrentClass());
        // is method public
        Method method = new Method();
        method.name = currentMethod;
        method.publicity = currentPubliciy;
        classData.methodsData.put(currentMethod, method);

        methods++;
    }

    @Override
    public void enterFieldDeclaration(JavaParser.FieldDeclarationContext ctx) {
        super.enterFieldDeclaration(ctx);
        String fieldName = ctx.variableDeclarators().variableDeclarator(0).variableDeclaratorId().getText();
        ClassData classData = classDataMap.get(getCurrentClass());
        Attribute attribute = new Attribute();
        attribute.name = fieldName;
        classData.attributesData.put(fieldName, attribute);

        attributes++;
    }

    @Override
    public void enterMethodCall(JavaParser.MethodCallContext ctx) {
        super.enterMethodCall(ctx);
        if(currentMethod != null) {
            // 💔🤢🤮
            RuleContext parent = ctx.getParent();
            if(parent instanceof JavaParser.ExpressionContext) {
                String attributeName = ((JavaParser.ExpressionContext) ctx.parent).start.getText();
                Attribute attribute = classDataMap.get(getCurrentClass()).attributesData.get(attributeName);
                if(attribute != null)
                    attribute.calledFrom.add(currentMethod);
            }
        }
    }

    @Override
    public void enterExpression(JavaParser.ExpressionContext ctx) {
        super.enterExpression(ctx);

        // get variables
        String attributeName = ctx.start.getText();
        Attribute attribute = classDataMap.get(getCurrentClass()).attributesData.get(attributeName);
        if(attribute != null) {
            attribute.calledFrom.add(currentMethod);
        }
    }

    @Override
    public void enterStatement(JavaParser.StatementContext ctx) {
        super.enterStatement(ctx);

        String[] ifKeywords = {"if", "else"};
        if (Arrays.asList(ifKeywords).contains(ctx.start.getText())) {
            incrementJumps();
        }

        // get variables
        if(ctx.getChildCount() > 0) {
            String statement = ctx.getText();
            if(statement.contains("=")) {
                String[] parts = statement.split("=");
                String variableName = parts[0].trim();
                String variableValue = parts[1].trim();
                ClassData classData = classDataMap.get(getCurrentClass());
                Attribute attribute = classData.attributesData.get(variableName);

                if (attribute != null) {
                    attribute.calledFrom.add(currentMethod);
                }
            }
        }
    }

    @Override
    public void enterElementValue(JavaParser.ElementValueContext ctx) {
        super.enterElementValue(ctx);
    }

    @Override
    public void enterMemberDeclaration(JavaParser.MemberDeclarationContext ctx) {
        super.enterMemberDeclaration(ctx);
    }

    // Count attribute accesses
    @Override
    public void enterPrimary(JavaParser.PrimaryContext ctx) {
        super.enterPrimary(ctx);
    }

    private String getCurrentClass() {
        return classStack.peek();
    }

    public Collection<ClassData> getClassesData() {
        return classDataMap.values();
    }

    private static class Method {
        private String name;
        private String publicity;
        private List<String> parameters;
        private List<String> localVariables;
        private List<String> attributes;
        private List<String> methods;
        private int jumps = 1;
    }

    private static class Attribute {
        private String name;
        private Set<String> calledFrom = new HashSet<>();
    }

    private static class ClassData {
        private String name;
        private final Map<String, Method> methodsData = new HashMap<>();
        private final Map<String, Attribute> attributesData = new HashMap<>();
        private double getWmc() {
            return methodsData.values().stream().mapToDouble(m -> m.jumps).sum();
        }
    }
}
