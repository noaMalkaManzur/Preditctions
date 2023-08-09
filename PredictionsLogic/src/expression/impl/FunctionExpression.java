package expression.impl;


import expression.api.AbstractExpression;
import expression.api.eExpression;
import expression.api.eFunctionExpression;

public abstract class FunctionExpression extends AbstractExpression {
    protected String arg;
    protected String functionName;
    protected eFunctionExpression typeFunctionExpression;

    public FunctionExpression(Object arg, eExpression typeExpression, eFunctionExpression typeFunctionExpression) {
        super(typeExpression);
        this.arg = (String) arg;
    }

    public abstract Object calculateExpression(String expressionString);

}


