package expression.impl;

import execution.context.Context;
import expression.api.eExpression;

public class PercentExpression extends FunctionExpression{
    public PercentExpression(eExpression typeExpression, String... args) {
        super(typeExpression, args);
    }
    @Override
    public Object calculateExpression(Context context) {
        if (Integer.parseInt(args[1])== 0) {
            throw new IllegalArgumentException("Second argument cannot be zero");
        }
        else{
            return Integer.parseInt(args[0]) /Integer.parseInt(args[1]);
        }
    }
}
