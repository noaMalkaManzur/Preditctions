package action.impl;

import action.api.AbstractAction;
import definition.entity.EntityDefinition;
import execution.context.Context;

public class KillAction extends AbstractAction {

    public KillAction(EntityDefinition entityDefinition) {
        super(entityDefinition,null);
    }

    @Override
    public void invoke(Context context) {
        context.removeEntity(context.getPrimaryEntityInstance());
    }

}
