package action.impl;

import action.api.AbstractAction;
import action.api.ActionType;
import definition.entity.EntityDefinition;
import execution.context.Context;
import expression.api.Expression;

import java.util.List;

public class KillAction extends AbstractAction {
    public KillAction(ActionType actionType, EntityDefinition entityDefinition, List<Expression> expressionList) {
        super(actionType, entityDefinition, null);
    }

    @Override
    public void invoke(Context context) {
        context.removeEntity(context.getPrimaryEntityInstance());
    }

}
