package expression.impl;

import expression.api.AbstractExpression;
import expression.api.eExpression;

public class NumericExpression extends AbstractExpression {
    public NumericExpression(eExpression eExpression) {
        super(eExpression.NUMERIC);
    }

    @Override
    public Object calculateExpression(String expressionString) {
        return 0;
    }
}
