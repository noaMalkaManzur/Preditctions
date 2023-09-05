package execution.instance.enitty.manager;

import definition.entity.EntityDefinition;
import definition.property.api.PropertyDefinition;
import execution.instance.enitty.EntityInstance;
import execution.instance.property.PropertyInstance;

import java.util.List;

public interface    EntityInstanceManager {

    EntityInstance createEntityInstance(EntityDefinition entityDefinition);
    List<EntityInstance> getInstances();
    void killEntity(int id);
    public int getCurrPopulation();
    public void setCurrPopulation(int currPopulation);
    public List<Integer> getKillList();
    public void clearKillList();
    public void setKillList(List<Integer> killList);
    public void executeKill(int id);
    public EntityInstance getEntityInstanceByName(String entityName);
    void addEntityInstance(EntityInstance entityInstanceToAdd);
    PropertyInstance createPropertyInstance(PropertyDefinition propertyDefinition);
}
