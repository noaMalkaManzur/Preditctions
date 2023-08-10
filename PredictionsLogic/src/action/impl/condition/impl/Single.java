package action.impl.condition.impl;

import action.api.ActionType;
import action.impl.condition.api.ConditionAction;
import definition.entity.EntityDefinition;
import execution.context.Context;
import expression.api.Expression;

import java.util.List;

public class Single extends ConditionAction {

    String operator;
    List<Expression> expressionList;
    protected Single(ActionType actionType, EntityDefinition entityDefinition, List<Expression> expressionList, String operator) {
        super(actionType, entityDefinition, expressionList);
        this.expressionList = expressionList;
        this.operator =operator;
    }

    @Override
    public boolean checkOperator(Context context) {
        Object valueByExpression =expressionList.get(0).calculateExpression();
        Object propValue =context.getPrimaryEntityInstance().getPropertyByName(getContextEntity().getName());

        switch (operator) {
            case "=":
                return propValue.equals(valueByExpression);
            case "!=":
                return !propValue.equals(valueByExpression);
            case"Bt":
                return Bt(context);
            case "Lt":
                return Lt(context);
            default:
                throw new IllegalArgumentException("invalid argument was given");
        }
    }
     private boolean Bt(Context context) {
        Number valueExpression = (Number) expressionList.get(0).calculateExpression();
        Number propertyValue = (Number) context.getPrimaryEntityInstance().getPropertyByName(getContextEntity().getName());
        return propertyValue.doubleValue() > valueExpression.doubleValue();
    }
    private boolean Lt(Context context) {
        Number valueExpression = (Number) expressionList.get(0).calculateExpression();
        Number propertyValue = (Number) context.getPrimaryEntityInstance().getPropertyByName(getContextEntity().getName());
        return propertyValue.doubleValue() < valueExpression.doubleValue();
    }

    @Override
    public void invoke(Context context) {
        if(checkOperator(context)){
            actionList.get(0).invoke(context);
        }
        else
        {
            if(actionList.get(1)!= null){
                actionList.get(1).invoke(context);
            }
        }
    }
}
