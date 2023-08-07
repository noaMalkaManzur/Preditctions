package execution.instance.enitty.manager;

import java.util.List;

import definition.entity.EntityDefinition;
import execution.instance.enitty.EntityInstance;

public interface EntityInstanceManager {

    EntityInstance create(EntityDefinition entityDefinition);
    List<EntityInstance> getInstances();

    void killEntity(int id);
}
