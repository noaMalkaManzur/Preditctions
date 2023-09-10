package expression.impl;

import execution.context.Context;
import expression.api.eExpression;

import java.util.Objects;

public class EvaluateExpression extends FunctionExpression{
    String entityName;
    String propertyName;
    public EvaluateExpression(String... args) {
        super(eExpression.FUNCTION, args);
        this.entityName = args[0];
        this.propertyName = args[1];
    }
    @Override
    public Object calculateExpression(Context context) {

        if(Objects.equals(entityName, context.getPrimaryEntityInstance().getEntityDef().getName()))
            return context.getPrimaryEntityInstance().getPropertyByName(propertyName).getValue();

        return context.getSecondaryEntityInstance().getPropertyByName(propertyName).getValue();
    }
    @Override
    public String toString(){
        return "evaluate(" +entityName+"." + propertyName + ")";
    }
}
