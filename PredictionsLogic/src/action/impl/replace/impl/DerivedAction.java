package action.impl.replace.impl;

import action.impl.replace.api.ReplaceAction;
import definition.entity.EntityDefinition;
import definition.entity.EntityDefinitionImpl;
import definition.property.api.PropertyDefinition;
import definition.secondaryEntity.api.SecondaryEntityDefinition;
import execution.context.Context;
import execution.instance.enitty.EntityInstance;
import execution.instance.enitty.EntityInstanceImpl;
import execution.instance.property.PropertyInstance;
import execution.instance.property.PropertyInstanceImpl;
import expression.api.Expression;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DerivedAction extends ReplaceAction {

    public DerivedAction(EntityDefinition entityDefinition, List<Expression> expressionList, String entityNameToKill, String entityNameToCreate, SecondaryEntityDefinition secondaryEntityDef) {
        super(entityDefinition, expressionList, entityNameToKill, entityNameToCreate, secondaryEntityDef);
    }

    public EntityInstance createEntityInstance(Context context) {

        EntityInstance entityInstanceToKill = context.getPrimaryEntityInstance();
        EntityDefinition entityDefinitionToKill = entityInstanceToKill.getEntityDef();
        EntityInstance entityInstanceToCreate = context.getSecondaryEntityInstance();
        EntityDefinition entityDefinitionToCreate;

        if (entityInstanceToCreate.getEntityDef().getName().equals(entityNameToCreate)) {
            entityDefinitionToCreate = entityInstanceToCreate.getEntityDef();
        } else {
            Optional<EntityDefinition> optionalEntityDefinition = context.getEntityManager()
                    .getInstances()
                    .stream()
                    .map(EntityInstance::getEntityDef)
                    .filter(entityDef -> entityDef.getName().equals(entityNameToCreate))
                    .findFirst();

            entityDefinitionToCreate = optionalEntityDefinition.orElse(null);
            entityInstanceToCreate = new EntityInstanceImpl(entityDefinitionToCreate, entityInstanceToKill.getId());
            if (entityDefinitionToCreate != null) {
                for (Map.Entry<String, PropertyDefinition> property : entityDefinitionToCreate.getProps().entrySet()) {
                  entityInstanceToCreate.getEntityDef().addPropertyDefinition(property.getValue());
                }
            }
        }

        if (entityDefinitionToCreate == null && entityInstanceToCreate!= null) {
            throw new IllegalArgumentException("Entity definition not found for " + entityNameToCreate);
        }

        EntityDefinition entityDefinitionRes = new EntityDefinitionImpl(entityNameToCreate);
        entityDefinitionRes.setPopulation(entityDefinitionToCreate.getPopulation() + 1);
        EntityInstance resEntityInstance = new EntityInstanceImpl(entityDefinitionRes, entityInstanceToKill.getId());

        EntityInstance finalEntityInstanceToCreate = entityInstanceToCreate;
        entityInstanceToCreate.getEntityDef().getProps().forEach((propertyNameToCreate, propertyDefinitionToCreate) -> {
            entityInstanceToKill.getEntityDef().getProps().forEach((propertyNameToKill, propertyDefinitionToKill) -> {

                if (propertyDefinitionToKill != null &&
                        propertyDefinitionToCreate.getType().equals(propertyDefinitionToKill.getType()) &&
                        propertyNameToCreate.equals(propertyNameToKill)) {
                    entityDefinitionRes.addPropertyDefinition(propertyDefinitionToKill);
                    resEntityInstance.addPropertyInstance(new PropertyInstanceImpl(propertyDefinitionToKill,entityInstanceToKill.getPropertyByName(propertyNameToCreate).getValue()));
                }
            });

            if (!resEntityInstance.getEntityDef().getProps().containsKey(propertyNameToCreate)) {
                entityDefinitionRes.addPropertyDefinition(propertyDefinitionToCreate);
                Object value = propertyDefinitionToCreate.generateValue();
                PropertyInstance newPropertyInstance = new PropertyInstanceImpl(propertyDefinitionToCreate, value);
                resEntityInstance.addPropertyInstance(newPropertyInstance);
            }
        });

        return resEntityInstance;
    }
}
