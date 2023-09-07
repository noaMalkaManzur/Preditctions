package expression.impl;

import execution.context.Context;
import expression.api.eExpression;

public class TickFunction extends FunctionExpression{
    String entityName;
    public TickFunction(String... args) {
        super(eExpression.FUNCTION, args);
        entityName = args[1];
    }
    //arg[1] = entity name
    // args[0] = property name
    @Override
    public Object calculateExpression(Context context) {
        int currTick = 0;
        if(entityName  == context.getPrimaryEntityInstance().toString())
            currTick = context.getPrimaryEntityInstance().getPropertyByName(args[0]).getCurrTickForValueChanged();
        else{
            currTick = context.getSecondaryEntityInstance().getPropertyByName(args[0]).getCurrTickForValueChanged();
        }
        return context.getCurrTick() - currTick;
    }
    @Override
    public String toString(){
        return "ticks(" +entityName+ "." +args[0] + ")";
    }
}
