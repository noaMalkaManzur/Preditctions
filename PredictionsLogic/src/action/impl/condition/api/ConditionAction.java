package action.impl.condition.api;

import action.api.AbstractAction;
import action.api.Action;
import action.api.ActionType;
import definition.entity.EntityDefinition;
import execution.context.Context;
import expression.api.Expression;

import java.util.List;

public abstract class ConditionAction extends AbstractAction {
    protected String singularity;
    protected List<Action> actionList;

    protected ConditionAction(ActionType actionType, EntityDefinition entityDefinition, List<Expression> expressionList) {
        super(actionType, entityDefinition, expressionList);
    }

    public abstract boolean checkOperator(Context context);
}
