package expression.impl;

import execution.context.Context;
import expression.api.eExpression;

public class EvaluateExpression extends FunctionExpression{
    public EvaluateExpression(String... args) {
        super(eExpression.FUNCTION, args);
    }
    @Override
    public Object calculateExpression(Context context) {
         return context.getEntityManager().getEntityInstanceByName(args[0]).getPropertyByName(args[1]).getValue();
    }
}
