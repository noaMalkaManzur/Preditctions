package expression.impl;

import execution.context.Context;
import expression.api.eExpression;

public class PropertyExpression extends FunctionExpression {

    public PropertyExpression(String arg) {
        super(arg,eExpression.PROPERTY);

    }
        @Override
        public Object calculateExpression(Context context) {
            return context.getPrimaryEntityInstance().getPropertyByName(arg).getValue();
        }

}

