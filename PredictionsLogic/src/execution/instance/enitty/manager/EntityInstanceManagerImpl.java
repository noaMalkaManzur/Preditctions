package execution.instance.enitty.manager;

import definition.entity.EntityDefinition;
import definition.property.api.PropertyDefinition;
import execution.instance.enitty.EntityInstance;
import execution.instance.enitty.EntityInstanceImpl;
import execution.instance.property.PropertyInstance;
import execution.instance.property.PropertyInstanceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntityInstanceManagerImpl implements EntityInstanceManager {

    private int entitiesCount;
    private List<EntityInstance> instances;
    private int currPopulation;
    private List<Integer> killList;
    private List<EntityInstance> replaceEntityList;

    public EntityInstanceManagerImpl() {

        entitiesCount = 0;
        instances = new ArrayList<>();
        killList = new ArrayList<>();
        replaceEntityList = new ArrayList<>();
    }
    @Override
    public EntityInstance createEntityInstance(EntityDefinition entityDefinition) {
        entitiesCount++;
        EntityInstance newEntityInstance = new EntityInstanceImpl(entityDefinition, entitiesCount);
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
        if(!killList.contains(id))
            killList.add(id);
    }

    @Override
    public void addReplaceEntityList(EntityInstance replaceEntity) {
        replaceEntityList.add(replaceEntity);
    }

    @Override
    public void executeKill(int id) {
        instances.removeIf(item -> item.getId() == id);
        setCurrPopulation(instances.size());
    }

    @Override
    public void addEntityInstance(EntityInstance entityInstanceToAdd) {
        instances.add(entityInstanceToAdd);
    }

    @Override
    public PropertyInstance createPropertyInstance(PropertyDefinition propertyDefinition) {

        Object value = propertyDefinition.generateValue();
        PropertyInstance newPropertyInstance = new PropertyInstanceImpl(propertyDefinition, value);

        return newPropertyInstance;
    }

    @Override
    public int getCurrPopulation() {
        return currPopulation;
    }
    @Override
    public void setCurrPopulation(int currPopulation) {
        this.currPopulation = currPopulation;
    }
    @Override
    public List<Integer> getKillList() {
        return killList;
    }

    @Override
    public List<EntityInstance> getReplaceEntityList() {
        return replaceEntityList;
    }

    @Override
    public void clearKillList()
    {
        killList.clear();
    }

    @Override
    public void ClearReplaceList() {
        replaceEntityList.clear();
    }
    @Override
    public void setKillList(List<Integer> killList) {
        this.killList = killList;
    }
}
