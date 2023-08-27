package execution.instance.enitty.manager;

import definition.entity.EntityDefinition;
import execution.instance.enitty.EntityInstance;

import java.util.List;

public interface EntityInstanceManager {

    EntityInstance create(EntityDefinition entityDefinition);

    List<EntityInstance> getInstances();

    void killEntity(int id);
    public int getCurrPopulation();

    public void setCurrPopulation(int currPopulation);
    public List<Integer> getKillList();
    public void clearKillList();
    public void setKillList(List<Integer> killList);
    public void executeKill(int id);
}
