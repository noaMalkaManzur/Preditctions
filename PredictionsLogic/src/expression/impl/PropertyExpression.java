package expression.impl;

import execution.context.Context;
import expression.api.eExpression;

public class PropertyExpression extends FunctionExpression {

    public PropertyExpression(String... args) {
        super(eExpression.PROPERTY, args);

    }
        @Override
        public Object calculateExpression(Context context) {
            return context.getPrimaryEntityInstance().getPropertyByName(args[0]).getValue();
        }
}

