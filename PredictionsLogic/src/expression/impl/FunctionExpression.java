package expression.impl;


import expression.api.AbstractExpression;
import expression.api.eExpression;
import expression.api.eFunctionExpression;

public abstract class FunctionExpression extends AbstractExpression {

    public FunctionExpression(Object arg,eExpression typeExpression, eFunctionExpression typeFunctionExpression ) {
        super(arg, eExpression.FUNCTION, typeFunctionExpression);
    }

    public abstract int calculateExpression(String expressionString);

}


