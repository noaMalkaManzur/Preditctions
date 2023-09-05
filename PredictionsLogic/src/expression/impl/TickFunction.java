package expression.impl;

import execution.context.Context;
import expression.api.eExpression;

public class TickFunction extends  FunctionExpression{
    String entityName;
    public TickFunction(String... args) {
        super(eExpression.FUNCTION, args);
        entityName = args[1];
    }
    @Override
    public Object calculateExpression(Context context) {
        this.entityName = context.getPrimaryEntityInstance().toString();
        return context.getCurrTick() - context.getPrimaryEntityInstance().getPropertyByName(args[0]).getCurrTickForValueChanged();
    }
    @Override
    public String toString(){
        return "ticks(" +entityName+ "." +args[0] + ")";
    }
}
