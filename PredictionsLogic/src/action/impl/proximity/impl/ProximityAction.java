package action.impl.proximity.impl;

import Enums.ActionTypeDTO;
import action.api.AbstractAction;
import definition.entity.EntityDefinition;
import execution.context.Context;
import expression.api.Expression;

import java.util.List;

public class ProximityAction  extends AbstractAction {
    public ProximityAction(EntityDefinition entityDefinition, List<Expression> expressionList) {
        super(ActionTypeDTO.PROXIMITY, entityDefinition, expressionList);
    }

    @Override
    public void invoke(Context context, int currTickToChangeValue) {

    }
}
