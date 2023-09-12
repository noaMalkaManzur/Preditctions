package execution.instance.enitty.manager;

import definition.entity.EntityDefinition;
import definition.property.api.PropertyDefinition;
import execution.instance.enitty.EntityInstance;
import execution.instance.property.PropertyInstance;

import java.util.List;

public interface EntityInstanceManager {

    EntityInstance createEntityInstance(EntityDefinition entityDefinition);
    List<EntityInstance> getInstances();
    void killEntity(int id);
    void addReplaceEntityList(EntityInstance replaceEntity);
    public int getCurrPopulation();
    public void setCurrPopulation(int currPopulation);
    public List<Integer> getKillList();
    public List<EntityInstance> getReplaceEntityList();
    public void clearKillList();
    public void ClearReplaceList();
    public void setKillList(List<Integer> killList);
    public void executeKill(int id);
    void addEntityInstance(EntityInstance entityInstanceToAdd);
    PropertyInstance createPropertyInstance(PropertyDefinition propertyDefinition);
}
