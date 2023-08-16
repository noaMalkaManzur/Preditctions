package action.impl.condition.impl;

import Enums.ActionTypeDTO;
import action.api.Action;
import action.impl.condition.api.ConditionAction;
import definition.entity.EntityDefinition;
import execution.context.Context;
import expression.api.Expression;

import java.util.List;

public class MultipleAction extends ConditionAction {
    List<ConditionAction> conditionList;
    String logic;

    public MultipleAction(ActionTypeDTO actionType, EntityDefinition entityDefinition, List<Expression> expressionList, List<Action> actionList, String propertyName, String logic, List<ConditionAction> conditionList) {
        super(actionType, entityDefinition, expressionList, actionList, propertyName);
        this.logic= logic;
        this.conditionList = conditionList;
    }


    @Override
    public boolean checkCondition(Context context) {
        boolean result;

        if (logic.equals("and")) {
            result = conditionList.stream().allMatch(condition -> condition.checkCondition(context));
        } else {
            result = conditionList.stream().anyMatch(condition -> condition.checkCondition(context));
        }

        return result;
    }

}
