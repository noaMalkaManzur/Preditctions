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

public class DerivedAction extends ReplaceAction {

    public DerivedAction(EntityDefinition entityDefinition, List<Expression> expressionList, String entityNameToKill, String entityNameToCreate, SecondaryEntityDefinition secondaryEntityDef) {
        super(entityDefinition, expressionList, entityNameToKill, entityNameToCreate, secondaryEntityDef);
    }

    @Override
    public EntityInstance createEntityInstance(Context context) {
        //todo: need to handle sending the entity to kill and entity to create
        //EntityInstance entityInstanceToKill = context.getEntityManager().getEntityInstanceByName(entityNameToKill);
        EntityInstance entityInstanceToKill = context.getPrimaryEntityInstance();
        EntityDefinition entityDefinitionToKill = entityInstanceToKill.getEntityDef();

        //EntityInstance entityInstanceToCreate = context.getEntityManager().getEntityInstanceByName(entityNameToCreate);
        EntityInstance entityInstanceToCreate = context.getSecondaryEntityInstance();
        EntityDefinition entityDefinitionToCreate = entityInstanceToCreate.getEntityDef();

        EntityDefinition entityDefinitionRes = new EntityDefinitionImpl(entityNameToCreate,entityDefinitionToKill.getPopulation());
        EntityInstance resEntityInstance = new EntityInstanceImpl(entityDefinitionRes, entityInstanceToKill.getId());

        entityDefinitionToCreate.getProps().forEach((propertyName, propertyDefinition)->{
            if(entityDefinitionToKill.getProps().containsKey(propertyName)){
                if(entityDefinitionToKill.getProps().get(propertyName).getType() == propertyDefinition.getType()) {
                    PropertyInstance propertyInstance = new PropertyInstanceImpl(entityDefinitionToKill.getProps().get(propertyName),entityInstanceToKill.getPropertyByName(propertyName).getValue());
                    resEntityInstance.addPropertyInstance(propertyInstance);
                }
            }
            else{
                resEntityInstance.addPropertyInstance(context.getEntityManager().createPropertyInstance(propertyDefinition));
            }
        });

        return resEntityInstance;
    }

}
