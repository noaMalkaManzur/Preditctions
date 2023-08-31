package execution.context;

import execution.instance.enitty.EntityInstance;
import execution.instance.enitty.manager.EntityInstanceManager;
import execution.instance.property.PropertyInstance;

public interface Context {
    EntityInstance getPrimaryEntityInstance();
    EntityInstance getSecondaryEntityInstance();
    void removeEntity(EntityInstance entityInstance);
    PropertyInstance getEnvironmentVariable(String name);
    void setPrimaryInstance(int idEntityInstance);
    EntityInstanceManager getEntityManager();
    void setCurrTick(int currTick);
    int getCurrTick();
    int getColumns();
    int getRows();


}
