package action.impl.replace.impl;

import action.impl.replace.api.ReplaceAction;
import definition.entity.EntityDefinition;
import definition.entity.EntityDefinitionImpl;
import definition.secondaryEntity.api.SecondaryEntityDefinition;
import execution.context.Context;
import execution.instance.enitty.EntityInstance;
import execution.instance.enitty.EntityInstanceImpl;
import expression.api.Expression;

import java.util.List;

public class ScratchAction extends ReplaceAction {
    public ScratchAction(EntityDefinition entityDefinition, List<Expression> expressionList, String entityNameToKill, String entityNameToCreate, SecondaryEntityDefinition secondaryEntityDef) {
        super(entityDefinition, expressionList, entityNameToKill, entityNameToCreate, secondaryEntityDef);
    }

    @Override
    public EntityInstance createEntityInstance(Context context) {

        EntityInstance entityInstanceToKill = context.getPrimaryEntityInstance();
        EntityInstance entityInstanceToCreate = context.getSecondaryEntityInstance();
        EntityDefinition entityDefinitionToCreate = entityInstanceToCreate.getEntityDef();
        EntityDefinition entityDefinitionRes = new EntityDefinitionImpl(entityNameToCreate);
        entityDefinitionRes.setPopulation(entityDefinitionToCreate.getPopulation());

        EntityInstance resEntityInstance = new EntityInstanceImpl(entityDefinitionRes,entityInstanceToKill.getId());

        entityDefinitionToCreate.getProps().forEach((propertyName, propertyDefinition)->{
            resEntityInstance.getEntityDef().addPropertyDefinition(propertyDefinition);
        });

        return resEntityInstance;
    }

}
