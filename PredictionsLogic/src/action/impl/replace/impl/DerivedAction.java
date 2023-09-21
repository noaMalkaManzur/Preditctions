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

public class DerivedAction extends ReplaceAction {

    public DerivedAction(EntityDefinition entityDefinition, List<Expression> expressionList, EntityDefinition entityDefKill, EntityDefinition entityDefToCreate, SecondaryEntityDefinition secondaryEntityDef) {
        super(entityDefinition, expressionList, entityDefKill, entityDefToCreate, secondaryEntityDef);

    }

    public EntityInstance createEntityInstance(Context context) {

        EntityInstance entityInstanceToKill = context.getPrimaryEntityInstance();
        EntityInstance entityInstanceToCreate = new EntityInstanceImpl(entityToKill, entityInstanceToKill.getId());

        for (Map.Entry<String, PropertyDefinition> property : entityToCreate.getProps().entrySet()) {
            entityInstanceToCreate.getEntityDef().addPropertyDefinition(property.getValue());
        }

        EntityDefinition entityDefinitionRes = new EntityDefinitionImpl(entityToCreate.getName());
        entityDefinitionRes.setPopulation(entityToCreate.getPopulation() + 1);
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
