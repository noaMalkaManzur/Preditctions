package expression.api;

import execution.context.Context;

public interface Expression {
    Object calculateExpression(Context context);
    eExpression getType();
    Object getArg();

}
