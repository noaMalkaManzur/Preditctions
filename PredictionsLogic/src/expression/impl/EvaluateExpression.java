package expression.impl;

import execution.context.Context;
import expression.api.eExpression;

import java.util.Objects;

public class EvaluateExpression extends FunctionExpression{
    String entityName;
    public EvaluateExpression(String... args) {
        super(eExpression.FUNCTION, args);
        this.entityName = args[0];
    }
    @Override
    public Object calculateExpression(Context context) {

        if(Objects.equals(entityName, context.getPrimaryEntityInstance().toString()))
            return context.getPrimaryEntityInstance().getPropertyByName(args[1]).getValue();

        return context.getSecondaryEntityInstance().getPropertyByName(args[1]).getValue();
    }
    @Override
    public String toString(){
        return "evaluate(" +entityName+"." + args[0] + ")";
    }
}
