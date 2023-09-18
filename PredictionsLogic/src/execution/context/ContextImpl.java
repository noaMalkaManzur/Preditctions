package execution.context;

import definition.world.api.WorldDefinition;
import definition.world.impl.Cell;
import definition.world.impl.Grid;
import execution.instance.enitty.EntityInstance;
import execution.instance.enitty.manager.EntityInstanceManager;
import execution.instance.environment.api.ActiveEnvironment;
import execution.instance.property.PropertyInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContextImpl implements Context {

    private EntityInstance primaryEntityInstance;
    private EntityInstance secondaryEntityInstance;
    private EntityInstanceManager entityInstanceManager;
    private ActiveEnvironment activeEnvironment;
    private int currTick;
    private List<EntityInstance> entitySecondaryInstances = new ArrayList<>();
    //private final WorldDefinition world;
    private Grid grid;


    public ContextImpl(EntityInstance primaryEntityInstance, EntityInstanceManager entityInstanceManager,
                       ActiveEnvironment activeEnvironment, EntityInstance secondaryEntityInstance, Grid grid) {

        this.primaryEntityInstance = primaryEntityInstance;
        this.entityInstanceManager = entityInstanceManager;
        this.activeEnvironment = activeEnvironment;
        this.secondaryEntityInstance = secondaryEntityInstance;
        this.grid = grid;
    }

    @Override
    public EntityInstance getPrimaryEntityInstance() {
        return primaryEntityInstance;
    }

    @Override
    public EntityInstance getSecondaryEntityInstance() {
        return secondaryEntityInstance;
    }

//    @Override
//    public WorldDefinition getWorld() {
//        return world;
//    }

    @Override
    public void removeEntity(EntityInstance entityInstance) {
        entityInstanceManager.killEntity(entityInstance.getId());
    }
    @Override
    public PropertyInstance getEnvironmentVariable(String name) {
        return activeEnvironment.getProperty(name);
    }

    @Override
    public void setPrimaryInstance(int idEntityInstance) {
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
    @Override
    public void setCurrTick(int currTick) {
        this.currTick = currTick;
    }
    @Override
    public int getCurrTick() {
        return currTick;
    }

    @Override
    public void setEntitySecondaryList(List<EntityInstance> entitySecondaryList) {
        this.entitySecondaryInstances = entitySecondaryList;
    }
    @Override
    public List<EntityInstance> getEntitySecondaryList() {
        return entitySecondaryInstances;
    }
    @Override
    public void setSecondEntity(EntityInstance secondaryEntityInstance) {
        this.secondaryEntityInstance =  secondaryEntityInstance;
    }
    @Override
    public Grid getGrid(){
        return grid;
    }

    @Override
    public List<Cell> getCells() {
        return grid.getCells();
    }
}
