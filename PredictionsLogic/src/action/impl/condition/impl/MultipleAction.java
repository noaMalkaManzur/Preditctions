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

    public MultipleAction(EntityDefinition entityDefinition, List<Expression> expressionList,
                          List<Action> thenActionList, List<Action> elseActionList, String propertyName, List<ConditionAction> conditionList, String logic, EntityDefinition secondaryEntityDef) {
        super(ActionTypeDTO.CONDITION, entityDefinition, expressionList, thenActionList, elseActionList, propertyName, secondaryEntityDef);
        this.conditionList = conditionList;
        this.logic = logic;
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
