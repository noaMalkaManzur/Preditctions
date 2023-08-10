package action.impl.condition.impl;

import action.api.Action;
import action.api.ActionType;
import action.impl.condition.api.ConditionAction;
import definition.entity.EntityDefinition;
import execution.context.Context;
import expression.api.Expression;

import java.util.List;

public class MultipleAction extends ConditionAction {
    List<ConditionAction> conditionList;
    String propertyName;
    String logic;

    protected MultipleAction(ActionType actionType, EntityDefinition entityDefinition, List<Expression> expressionList, String propertyName, List<Action> actionList) {
        super(actionType, entityDefinition, expressionList, propertyName, actionList);
        this.logic= logic;
        this.propertyName= propertyName;
    }


    @Override
    public boolean checkCondition(Context context){
        if(logic.equals("and")){
            return conditionList.get(0).checkCondition(context) && conditionList.get(1).checkCondition(context);

        }
        return conditionList.get(0).checkCondition(context) || conditionList.get(1).checkCondition(context);
    }

}
