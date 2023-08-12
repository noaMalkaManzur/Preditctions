package execution.instance.enitty.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import definition.entity.EntityDefinition;
import definition.property.api.PropertyDefinition;
import execution.instance.enitty.EntityInstance;
import execution.instance.enitty.EntityInstanceImpl;
import execution.instance.property.PropertyInstance;
import execution.instance.property.PropertyInstanceImpl;

public class EntityInstanceManagerImpl implements EntityInstanceManager {

    private int count;
    private List<EntityInstance> instances;

    public EntityInstanceManagerImpl() {
        count = 0;
        instances = new ArrayList<>();
    }

    @Override
    public EntityInstance create(EntityDefinition entityDefinition) {
        count++;
        EntityInstance newEntityInstance = new EntityInstanceImpl(entityDefinition, count);
        instances.add(newEntityInstance);

        for (Map.Entry<String, PropertyDefinition> propertyDefinition : entityDefinition.getProps().entrySet()) {
            Object value = propertyDefinition.getValue().generateValue();
            PropertyInstance newPropertyInstance = new PropertyInstanceImpl(propertyDefinition.getValue(), value);
            newEntityInstance.addPropertyInstance(newPropertyInstance);
        }
        return newEntityInstance;
    }

    @Override
    public List<EntityInstance> getInstances() {
        return instances;
    }

    @Override
    public void killEntity(int id) {
        instances.remove(id);
    }
}
