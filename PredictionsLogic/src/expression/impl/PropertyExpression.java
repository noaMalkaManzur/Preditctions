package expression.impl;

import execution.context.Context;
import expression.api.AbstractExpression;
import expression.api.eExpression;

public class PropertyExpression extends AbstractExpression {

    private Context context;
    public PropertyExpression(eExpression eExpression) {
        super(eExpression.PROPERTY);
    }
        @Override
        public Object calculateExpression(String expressionString) {
            return context.getPrimaryEntityInstance().getPropertyByName(expressionString).getValue();
        }
}

