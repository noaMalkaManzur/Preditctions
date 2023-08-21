package execution.context;

import execution.instance.enitty.EntityInstance;
import execution.instance.enitty.manager.EntityInstanceManager;
import execution.instance.property.PropertyInstance;

public interface Context {
    EntityInstance getPrimaryEntityInstance();
    void removeEntity(EntityInstance entityInstance);
    PropertyInstance getEnvironmentVariable(String name);
    void setPrimaryInstacne(int idEntityInstance);
    EntityInstanceManager getEntityManager();

    void setCurrTick(int currTick);
    int getCurrTick();

}
