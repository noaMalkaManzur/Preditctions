package execution.instance.enitty;

import definition.entity.EntityDefinition;
import execution.instance.property.PropertyInstance;

public interface EntityInstance {
    int getId();
    PropertyInstance getPropertyByName(String name);
    void addPropertyInstance(PropertyInstance propertyInstance);
    EntityDefinition getEntityDef();
}
