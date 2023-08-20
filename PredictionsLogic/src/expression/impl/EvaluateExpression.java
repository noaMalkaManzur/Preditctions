package expression.impl;

import execution.context.Context;
import expression.api.eExpression;

public class EvaluateExpression extends FunctionExpression{
    public EvaluateExpression(eExpression typeExpression, String... args) {
        super(typeExpression, args);
    }
    //todo:check about getting the arg for entity name
    @Override
    public Object calculateExpression(Context context) {
         return context.getPrimaryEntityInstance().getPropertyByName(args[0]).getValue();
    }
}
