package execution.context;

import execution.instance.enitty.EntityInstance;
import execution.instance.enitty.manager.EntityInstanceManager;
import execution.instance.environment.api.ActiveEnvironment;
import execution.instance.property.PropertyInstance;

import java.util.Optional;

public class ContextImpl implements Context {

    private EntityInstance primaryEntityInstance;
    private EntityInstanceManager entityInstanceManager;
    private ActiveEnvironment activeEnvironment;

    public ContextImpl(EntityInstance primaryEntityInstance, EntityInstanceManager entityInstanceManager, ActiveEnvironment activeEnvironment) {
        this.primaryEntityInstance = primaryEntityInstance;
        this.entityInstanceManager = entityInstanceManager;
        this.activeEnvironment = activeEnvironment;
    }

    @Override
    public EntityInstance getPrimaryEntityInstance() {
        return primaryEntityInstance;
    }

    @Override
    public void removeEntity(EntityInstance entityInstance) {
        entityInstanceManager.killEntity(entityInstance.getId());
    }

    @Override
    public PropertyInstance getEnvironmentVariable(String name) {
        return activeEnvironment.getProperty(name);
    }

    @Override
    public void setPrimaryInstacne(int idEntityInstance) {
        Optional<EntityInstance> foundInstance = entityInstanceManager.getInstances()
                .stream()
                .filter(instance -> instance.getId() == idEntityInstance)
                .findAny();
        foundInstance.ifPresent(entityInstance -> primaryEntityInstance = entityInstance);
    }

    @Override
    public EntityInstanceManager getEntityManager() {
        return entityInstanceManager;
    }
}
