package action.impl.replace.api;

import Enums.ActionTypeDTO;
import action.api.AbstractAction;
import action.impl.KillAction;
import definition.entity.EntityDefinition;
import definition.secondaryEntity.api.SecondaryEntityDefinition;
import execution.context.Context;
import execution.instance.enitty.EntityInstance;
import expression.api.Expression;

import java.util.List;

public abstract class ReplaceAction extends AbstractAction {
    protected EntityDefinition entityToKill;
    protected EntityDefinition entityToCreate;
    public ReplaceAction(EntityDefinition entityDefinition, List<Expression> expressionList, EntityDefinition entityToKill, EntityDefinition entityToCreate, SecondaryEntityDefinition secondaryEntityDef) {
        super(ActionTypeDTO.REPLACE, entityDefinition, expressionList, secondaryEntityDef);
        this.entityToKill = entityToKill;
        this.entityToCreate = entityToCreate;
    }
    public abstract EntityInstance createEntityInstance(Context context);
    @Override
    public void invoke(Context context, int currTickToChangeValue) {

        EntityInstance entityInstanceToKill = context.getPrimaryEntityInstance();
        EntityDefinition entityDefinitionToKill = entityInstanceToKill.getEntityDef();
        KillAction killAction = new KillAction(entityDefinitionToKill, null);
        killAction.invoke(context, currTickToChangeValue);
        EntityInstance entityInstance = createEntityInstance(context);
        entityInstance.setCoordinate(entityInstanceToKill.getCoordinate());
        context.getEntityManager().addReplaceEntityList(entityInstance);
    }
}
