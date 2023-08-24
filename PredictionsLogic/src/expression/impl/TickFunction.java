package expression.impl;

import execution.context.Context;
import expression.api.eExpression;

public class TickFunction extends  FunctionExpression{

    public TickFunction(eExpression typeExpression, String... args) {
        super(typeExpression, args);
    }
    //todo: check if arg[0] has the property name
    @Override
    public Object calculateExpression(Context context) {
        return context.getCurrTick() - context.getPrimaryEntityInstance().getPropertyByName(args[0]).getCurrTickForValueChanged();
    }
}
