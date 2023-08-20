package action.impl;

import Enums.ActionTypeDTO;
import action.api.AbstractAction;
import definition.entity.EntityDefinition;
import execution.context.Context;

public class KillAction extends AbstractAction {
    public KillAction(EntityDefinition entityDefinition) {

        super(ActionTypeDTO.KILL, entityDefinition, null, 0);
    }

    @Override
    public void invoke(Context context) {
        context.removeEntity(context.getPrimaryEntityInstance());
    }

}
