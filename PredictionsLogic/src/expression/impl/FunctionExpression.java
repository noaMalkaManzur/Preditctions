package expression.impl;


import expression.api.AbstractExpression;
import expression.api.eExpression;

public abstract class FunctionExpression extends AbstractExpression {
    protected String arg;
    public FunctionExpression(String arg, eExpression typeExpression) {
        super(typeExpression);
        this.arg = arg;
    }

    public abstract Object calculateExpression();

}


