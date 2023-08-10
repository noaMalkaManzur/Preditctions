package action.impl.condition.impl;

import action.api.Action;
import action.api.ActionType;
import action.impl.condition.api.ConditionAction;
import definition.entity.EntityDefinition;
import execution.context.Context;
import expression.api.Expression;

import java.util.List;

public class SingleAction extends ConditionAction {

    String operator;
    List<Expression> expressionList;

    public SingleAction(ActionType actionType, EntityDefinition entityDefinition, List<Expression> expressionList, String singularity, List<Action> actionList, String operator) {
        super(actionType, entityDefinition, expressionList, singularity, actionList);
        this.expressionList = expressionList;
        this.operator =operator;
    }


    @Override
    public boolean checkCondition(Context context) {
        Object valueByExpression = expressionList.get(0).calculateExpression();
        Object propValue =context.getPrimaryEntityInstance().getPropertyByName(propertyName);

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

}
