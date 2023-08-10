package expression.impl;

import execution.context.Context;
import expression.api.eExpression;

public class PropertyExpression extends FunctionExpression {

    private Context context;

    public PropertyExpression(String arg, eExpression typeExpression, Context context) {
        super(arg, typeExpression);
        this.context = context;
    }

        @Override
        public Object calculateExpression() {
            return context.getPrimaryEntityInstance().getPropertyByName(arg).getValue();
        }
}

