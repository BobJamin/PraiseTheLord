package com.bobjamin.kratosplugin.antlr.java;

import com.bobjamin.kratosplugin.antlr.common.*;
import com.bobjamin.kratosplugin.models.AccessModifier;
import com.bobjamin.kratosplugin.models.Attribute;
import com.bobjamin.kratosplugin.models.Class;
import com.bobjamin.kratosplugin.models.Method;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomJavaParserListener extends JavaParserBaseListener implements ExpressionVisitor {
    private final Stack<Class> classStack = new Stack<>();
    private final List<Class> classes = new ArrayList<>();
    private Method currentMethod = null;
    private AccessModifier currentAccessModifier = null;

    @Override
    public List<Class> getClasses() {
        return classes;
    }

    @Override
    public void enterModifier(JavaParser.ModifierContext ctx) {
        super.enterModifier(ctx);
        if (AccessModifier.contains(ctx.getText().toUpperCase())) {
            currentAccessModifier = AccessModifier.valueOf(ctx.getText().toUpperCase());
        }
    }

    @Override
    public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        Class _class = new Class(ctx.identifier().getText());
        classStack.push(_class);
        classes.add(_class);
    }

    @Override
    public void enterFieldDeclaration(JavaParser.FieldDeclarationContext ctx) {
        super.enterFieldDeclaration(ctx);
        Attribute newAttribute = new Attribute(ctx.variableDeclarators().variableDeclarator(0).variableDeclaratorId().getText(), ctx.typeType().getText());
        getCurrentClass().addAttribute(newAttribute);
    }

    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        super.enterMethodDeclaration(ctx);
        currentMethod = new Method(ctx.identifier().getText(), currentAccessModifier);
        getCurrentClass().addMethod(currentMethod);
    }


    @Override
    public void enterFormalParameter(JavaParser.FormalParameterContext ctx) {
        super.enterFormalParameter(ctx);
        Attribute newParameter = new Attribute(ctx.variableDeclaratorId().getText(), ctx.typeType().getText());
        if(getCurrentMethod() != null)
            getCurrentMethod().addParameter(newParameter);
    }

    @Override
    public void enterLocalVariableDeclaration(JavaParser.LocalVariableDeclarationContext ctx) {
        super.enterLocalVariableDeclaration(ctx);
        Attribute newVariable = new Attribute(ctx.variableDeclarators().variableDeclarator(0).variableDeclaratorId().getText(), ctx.typeType().getText());
        if(getCurrentMethod() != null)
            getCurrentMethod().addVariable(newVariable);
    }


    @Override
    public void enterSwitchLabel(JavaParser.SwitchLabelContext ctx) {
        super.enterSwitchLabel(ctx);
        if(getCurrentMethod() != null)
            getCurrentMethod().incrementJumps();
    }

    @Override
    public void enterCatchClause(JavaParser.CatchClauseContext ctx) {
        super.enterCatchClause(ctx);
        if(getCurrentMethod() != null)
            getCurrentMethod().incrementJumps();
    }

    @Override
    public void enterFinallyBlock(JavaParser.FinallyBlockContext ctx) {
        super.enterFinallyBlock(ctx);
        if(getCurrentMethod() != null)
            getCurrentMethod().incrementJumps();
    }

    @Override
    public void enterExpression(JavaParser.ExpressionContext ctx) {
        super.enterExpression(ctx);
        checkForExternalAccess(ctx.getText());
        checkForAttributeCall(ctx.start.getText());
    }

    @Override
    public void enterStatement(JavaParser.StatementContext ctx) {
        super.enterStatement(ctx);
        String[] ifKeywords = {"if", "else"};
        if (Arrays.asList(ifKeywords).contains(ctx.start.getText())) {
            getCurrentMethod().incrementJumps();
        }
    }

    @Override
    public void exitMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        super.enterMethodDeclaration(ctx);
        currentMethod = null;
    }

    @Override
    public void exitClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        classStack.pop();
    }

    private Class getCurrentClass() {
        return classStack.peek();
    }

    private Method getCurrentMethod() {
        return currentMethod;
    }

    private void checkForAttributeCall(String attributeName) {
        for(String attributePart : attributeName.split("\\.")) {
            Attribute attribute = getCurrentClass().getAttribute(attributePart);
            if (attribute != null && currentMethod != null) {
                attribute.addCalledFrom(currentMethod.getMethodName());
                currentMethod.addAttributeAccesses(attributePart);
            }
        }
    }

    private void checkForExternalAccess(String expression) {
        String[] splittedCall = expression.split("[.](?![^\\(\\[]*[\\]\\)])");
        for(int i = 0; i < splittedCall.length - 1; i++) {
            if(isExternalAttribute(splittedCall[i]) && accessesToAttribute(splittedCall[i + 1])) {
                getCurrentClass().addExternalAccess(getExternalType(splittedCall[i]));
            }
        }
    }

    private boolean isExternalAttribute(String expression) {
        return Boolean.TRUE.equals(this.findFirst(
                Optional.ofNullable(tryFindMember(expression)).map(a -> !a.getType().contentEquals(getCurrentClass().getClassName())).orElse(null),
                expression.matches("^new.*[(].*[)]$"),
                false
        ));
    }

    private boolean accessesToAttribute(String expression){
        return expression.matches("^(?!.*[(][)]$).*|get.*$");
    }

    private String getExternalType(String expression) {
        return this.findFirst(
                Optional.ofNullable(tryFindMember(expression)).map(Attribute::getType).orElse(null),
                tryGetTypeFromInstanciation(expression));
    }

    private String tryGetTypeFromInstanciation(String expression) {
        Matcher matcher = Pattern.compile("^new(.*)[(].*[)]$").matcher(expression);
        if(matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }


    private Attribute tryFindMember(String memberName) {
        return this.findFirst(
                Optional.ofNullable(getCurrentMethod()).map(m -> m.getVariable(memberName)).orElse(null),
                Optional.ofNullable(getCurrentMethod()).map(m -> m.getParameter(memberName)).orElse(null),
                Optional.ofNullable(getCurrentClass()).map(m -> m.getAttribute(memberName)).orElse(null));
    }

    @SafeVarargs
    private <T> T findFirst(T... params) {
        for(T p : params) {
            if(p != null)
                return p;
        }
        return null;
    }
}
