package action.impl.condition.api;

import Enums.ActionTypeDTO;
import action.api.AbstractAction;
import action.api.Action;
import definition.entity.EntityDefinition;
import execution.context.Context;
import expression.api.Expression;

import java.util.List;

public abstract class ConditionAction extends AbstractAction {
    //protected String singularity;
    protected List<Action> actionList;
    protected String propertyName;

    protected ConditionAction(ActionTypeDTO actionType, EntityDefinition entityDefinition, List<Expression> expressionList, List<Action> actionList, String propertyName ) {
        super(actionType, entityDefinition, expressionList);
        this.actionList = actionList;
        this.propertyName = propertyName;
    }


    public abstract boolean checkCondition(Context context);

    @Override
    public void invoke(Context context) {

        if (this.checkCondition(context))
        {
            actionList.get(0).invoke(context);
        } else
        {
            if (actionList.size() > 1) {
                actionList.get(1).invoke(context);
            }
        }

    }
}
