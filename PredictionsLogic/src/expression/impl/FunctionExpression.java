package expression.impl;


import expression.api.AbstractExpression;
import expression.api.eExpression;
import expression.api.eFunctionExpression;

public class FunctionExpression extends AbstractExpression {

    FunctionExpression(Object arg,eExpression typeExpression, eFunctionExpression typeFunctionExpression ) {
        super(arg, eExpression.FUNCTION, typeFunctionExpression);
    }



}


