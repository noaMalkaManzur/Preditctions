package action.impl.replace.impl;

import action.impl.replace.api.ReplaceAction;
import definition.entity.EntityDefinition;
import definition.entity.EntityDefinitionImpl;
import definition.secondaryEntity.api.SecondaryEntityDefinition;
import execution.context.Context;
import execution.instance.enitty.EntityInstance;
import execution.instance.enitty.EntityInstanceImpl;
import execution.instance.property.PropertyInstance;
import execution.instance.property.PropertyInstanceImpl;
import expression.api.Expression;

import java.util.List;

public class ScratchAction extends ReplaceAction {
    public ScratchAction(EntityDefinition entityDefinition, List<Expression> expressionList, EntityDefinition entityToKill, EntityDefinition entityToCreate, SecondaryEntityDefinition secondaryEntityDef) {
        super(entityDefinition, expressionList, entityToKill, entityToCreate, secondaryEntityDef);
    }

    @Override
    public EntityInstance createEntityInstance(Context context) {

        EntityInstance entityInstanceToKill = context.getPrimaryEntityInstance();
        EntityInstance entityInstanceToCreate = context.getSecondaryEntityInstance();

        EntityDefinition entityDefinitionToCreate = entityInstanceToCreate.getEntityDef();
        EntityDefinition entityDefinitionRes = new EntityDefinitionImpl(entityToCreate.getName());
        entityDefinitionRes.setPopulation(entityDefinitionToCreate.getPopulation());

        EntityInstance resEntityInstance = new EntityInstanceImpl(entityDefinitionRes,entityInstanceToKill.getId());

        entityInstanceToCreate.getEntityDef().getProps().forEach((propertyName, propertyDefinition)->{
            resEntityInstance.getEntityDef().addPropertyDefinition(propertyDefinition);
            Object value = propertyDefinition.generateValue();
            PropertyInstance newPropertyInstance = new PropertyInstanceImpl(propertyDefinition, value);
            resEntityInstance.addPropertyInstance(newPropertyInstance);
        });
        return resEntityInstance;
    }
}
