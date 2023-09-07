package expression.impl;

import execution.context.Context;
import expression.api.eExpression;

public class EvaluateExpression extends FunctionExpression{
    String entityName;
    public EvaluateExpression(String... args) {
        super(eExpression.FUNCTION, args);
        this.entityName = args[1];
    }
    @Override
    public Object calculateExpression(Context context) {

        if(entityName  == context.getPrimaryEntityInstance().toString())
            return context.getPrimaryEntityInstance().getPropertyByName(args[0]).getValue();

        return context.getSecondaryEntityInstance().getPropertyByName(args[0]).getValue();
    }
    @Override
    public String toString(){
        return "evaluate(" +entityName+"." + args[0] + ")";
    }
}
