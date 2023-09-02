package action.impl;

import Enums.ActionTypeDTO;
import action.api.AbstractAction;
import definition.entity.EntityDefinition;
import execution.context.Context;

public class KillAction extends AbstractAction {
    public KillAction(EntityDefinition entityDefinition, EntityDefinition secondaryEntityDef) {
        super(ActionTypeDTO.KILL, entityDefinition, null, secondaryEntityDef);
    }

    @Override
    public void invoke(Context context, int currTickToChangeValue) {
        context.removeEntity(context.getPrimaryEntityInstance());
    }

}
