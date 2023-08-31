package action.impl.replace.impl;

import action.impl.replace.api.ReplaceAction;
import definition.entity.EntityDefinition;
import definition.entity.EntityDefinitionImpl;
import execution.context.Context;
import execution.instance.enitty.EntityInstance;
import execution.instance.enitty.EntityInstanceImpl;
import expression.api.Expression;

import java.util.List;

public class ScratchAction extends ReplaceAction {
    public ScratchAction(EntityDefinition entityDefinition, List<Expression> expressionList, String entityNameToKill, String entityNameToCreate, EntityDefinition secondaryEntityDef) {
        super(entityDefinition, expressionList, entityNameToKill, entityNameToCreate, secondaryEntityDef);
    }

    @Override
    public EntityInstance createEntityInstance(Context context) {

        EntityInstance entityInstanceToKill = context.getEntityManager().getEntityInstanceByName(entityNameToKill);
        EntityInstance entityInstanceToCreate = context.getEntityManager().getEntityInstanceByName(entityNameToCreate);
        EntityDefinition entityDefinitionToCreate = entityInstanceToCreate.getEntityDef();
        EntityDefinition entityDefinitionRes = new EntityDefinitionImpl(entityNameToCreate,entityDefinitionToCreate.getPopulation());

        EntityInstance resEntityInstance = new EntityInstanceImpl(entityDefinitionRes,entityInstanceToKill.getId());

        entityDefinitionToCreate.getProps().forEach((propertyName, propertyDefinition)->{
            resEntityInstance.addPropertyInstance(context.getEntityManager().createPropertyInstance(propertyDefinition));

        });

        return resEntityInstance;

    }

}
