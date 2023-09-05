package expression.impl;

import execution.context.Context;
import expression.api.eExpression;

public class EvaluateExpression extends FunctionExpression{
    String entityName;
    public EvaluateExpression(String... args) {
        super(eExpression.FUNCTION, args);
    }
    @Override
    public Object calculateExpression(Context context) {
        //todo: ask noam if to do get secondary Entity
        this.entityName = context.getPrimaryEntityInstance().toString();
        return context.getPrimaryEntityInstance().getPropertyByName(args[0]).getValue();
         //return context.getEntityManager().getEntityInstanceByName(args[0]).getPropertyByName(args[1]).getValue();
    }
    @Override
    public String toString(){
        return "evaluate(" +entityName+"." + args[0] + ")";
    }
}
