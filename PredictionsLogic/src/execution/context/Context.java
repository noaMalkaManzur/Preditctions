package execution.context;

import definition.world.api.WorldDefinition;
import definition.world.impl.Cell;
import definition.world.impl.Grid;
import execution.instance.enitty.EntityInstance;
import execution.instance.enitty.manager.EntityInstanceManager;
import execution.instance.property.PropertyInstance;

import java.util.List;

public interface Context {
    EntityInstance getPrimaryEntityInstance();
    EntityInstance getSecondaryEntityInstance();

    //WorldDefinition getWorld();
    void removeEntity(EntityInstance entityInstance);
    PropertyInstance getEnvironmentVariable(String name);
    void setPrimaryInstance(int idEntityInstance);
    EntityInstanceManager getEntityManager();
    void setCurrTick(int currTick);
    int getCurrTick();

    void setEntitySecondaryList(List<EntityInstance> entitySecondaryList);
    List<EntityInstance> getEntitySecondaryList();
    void setSecondEntity(EntityInstance secondaryEntityInstance);
    Grid getGrid();
    List<Cell> getCells();
}
