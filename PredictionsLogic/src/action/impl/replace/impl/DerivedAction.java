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

public class DerivedAction extends ReplaceAction {

    public DerivedAction(EntityDefinition entityDefinition, List<Expression> expressionList, String entityNameToKill, String entityNameToCreate, SecondaryEntityDefinition secondaryEntityDef) {
        super(entityDefinition, expressionList, entityNameToKill, entityNameToCreate, secondaryEntityDef);
    }

    @Override
    public EntityInstance createEntityInstance(Context context) {
        EntityInstance entityInstanceToKill = context.getPrimaryEntityInstance();
        EntityDefinition entityDefinitionToKill = entityInstanceToKill.getEntityDef();

        //List<EntityInstance> entitySecondaryList =context.getEntitySecondaryList();
        EntityInstance entityInstanceToCreate = context.getSecondaryEntityInstance();
        EntityDefinition entityDefinitionToCreate = entityInstanceToCreate.getEntityDef();

        EntityDefinition entityDefinitionRes = new EntityDefinitionImpl(entityNameToCreate);
        entityDefinitionRes.setPopulation(entityDefinitionToKill.getPopulation());
        EntityInstance resEntityInstance = new EntityInstanceImpl(entityDefinitionRes, entityInstanceToKill.getId());

        entityDefinitionToCreate.getProps().forEach((propertyNameToCreate, propertyDefinitionToCreate)->{
            entityInstanceToKill.getEntityDef().getProps().forEach((propertyNameToKill, propertyDefinitionToKill)->{
                if(propertyNameToKill == propertyNameToCreate && propertyDefinitionToCreate.getType() == propertyDefinitionToKill.getType()){
                    entityDefinitionRes.addPropertyDefinition(propertyDefinitionToCreate);
                }
            });
        });
        resEntityInstance.setCoordinate(entityInstanceToKill.getCoordinate());
        return resEntityInstance;
    }

}/*   entityDefinitionToCreate.getProps().forEach((propertyName, propertyDefinition)->{
            if(entityDefinitionToKill.getProps().containsKey(propertyName)){
                if(entityDefinitionToKill.getProps().get(propertyName).getType() == propertyDefinition.getType()) {
                    resEntityInstance.getEntityDef().addPropertyDefinition(propertyDefinition);

                }
            }
            else{
                resEntityInstance.addPropertyInstance(context.getEntityManager().createPropertyInstance(propertyDefinition));
            }
        });*/
