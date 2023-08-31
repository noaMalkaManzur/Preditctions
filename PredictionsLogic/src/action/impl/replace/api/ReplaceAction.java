package action.impl.replace.api;

import Enums.ActionTypeDTO;
import action.api.AbstractAction;
import action.impl.KillAction;
import definition.entity.EntityDefinition;
import execution.context.Context;
import execution.instance.enitty.EntityInstance;
import expression.api.Expression;

import java.util.List;

public abstract class ReplaceAction extends AbstractAction {
    protected String entityNameToKill;
    protected String entityNameToCreate;
    public ReplaceAction(EntityDefinition entityDefinition, List<Expression> expressionList, String entityNameToKill, String entityNameToCreate) {
        super(ActionTypeDTO.REPLACE, entityDefinition, expressionList);
        this.entityNameToKill = entityNameToKill;
        this.entityNameToCreate = entityNameToCreate;
    }
    public abstract EntityInstance createEntityInstance(Context context);
    @Override
    public void invoke(Context context, int currTickToChangeValue) {
        EntityInstance entityInstanceToKill = context.getEntityManager().getEntityInstanceByName(entityNameToKill);
        EntityDefinition entityDefinitionToKill = entityInstanceToKill.getEntityDef();

        KillAction killAction = new KillAction(entityDefinitionToKill);
        killAction.invoke(context, currTickToChangeValue);
        EntityInstance entityInstance =  createEntityInstance(context);
        context.getEntityManager().addEntityInstance(entityInstance);


    }
}
