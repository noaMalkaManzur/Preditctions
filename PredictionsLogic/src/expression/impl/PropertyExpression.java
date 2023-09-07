package expression.impl;

import execution.context.Context;
import expression.api.eExpression;

public class PropertyExpression extends FunctionExpression {

    public PropertyExpression(String... args) {
        super(eExpression.PROPERTY, args);

    }

    @Override
    public Object calculateExpression(Context context) {

        if(context.getSecondaryEntityInstance() != null) {
            String nameSecondaryEntity = context.getSecondaryEntityInstance().getEntityDef().getName();
            if(context.getPrimaryEntityInstance().getEntityDef().getName()  == nameSecondaryEntity){
                return context.getSecondaryEntityInstance().getPropertyByName(args[0]).getValue();
            }

        }
        return context.getPrimaryEntityInstance().getPropertyByName(args[0]).getValue();
    }

    @Override
    public String toString(){
        return "property(" + args[0] + ")";
    }
}

