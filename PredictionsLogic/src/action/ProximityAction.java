package action;

import Enums.ActionTypeDTO;
import action.api.AbstractAction;
import definition.entity.EntityDefinition;
import execution.context.Context;
import expression.api.Expression;

import java.util.List;

public class ProximityAction  extends AbstractAction {
    protected ProximityAction(ActionTypeDTO actionType, EntityDefinition entityDefinition, List<Expression> expressionList) {
        super(actionType, entityDefinition, expressionList);
    }

    @Override
    public void invoke(Context context, int currTickToChangeValue) {

    }
}
