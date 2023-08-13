package action.impl.condition.impl;

import action.api.Action;
import action.impl.condition.api.ConditionAction;
import definition.entity.EntityDefinition;
import execution.context.Context;
import expression.api.Expression;

import java.util.List;

public class MultipleAction extends ConditionAction {
    List<ConditionAction> conditionList;
    String propertyName;
    String logic;

    public MultipleAction(EntityDefinition entityDefinition ,List<Expression> expressionList,String logic,String propertyName, List<Action> actionList,List<ConditionAction> conditionList) {
        super(entityDefinition, expressionList, propertyName, actionList);
        this.logic= logic;
        this.propertyName= propertyName;
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
