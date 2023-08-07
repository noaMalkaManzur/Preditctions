package expression.impl;


import expression.api.Expression;
import expression.api.eFunctionExpression;

public class FunctionExpression implements Expression {

    @Override
    public void evaluateExpression(String expressionString) {


    }
    private boolean isFunctionNameExist(String functionName) {

        for (eFunctionExpression validFunction : eFunctionExpression.values()) {
            if (validFunction.name().equals(functionName)) {
                return true;
            }
        }
        return false;
    }

    private FunctionExpression(String expressionString) {

        if (expressionString.matches("^\\w+\\(\\w+\\)$")) {
            int openParenIndex = expressionString.indexOf('(');
            int closeParenIndex = expressionString.indexOf(')');

            String functionName = expressionString.substring(0, openParenIndex);
            String argument = expressionString.substring(openParenIndex + 1, closeParenIndex);
            if(isFunctionNameExist(functionName)){
                //activeFunction;
            }
            else{
                //need to call properyexpression
            }

        }
    }
}


