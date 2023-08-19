package expression.impl;

import execution.context.Context;
import expression.api.eExpression;

public class EnvironmentFunction extends FunctionExpression {

    public EnvironmentFunction(String arg) {
        super(arg, eExpression.FUNCTION);

    }
    @Override
    public Object calculateExpression(Context context) {
        return context.getEnvironmentVariable(arg).getValue();
    }


}
