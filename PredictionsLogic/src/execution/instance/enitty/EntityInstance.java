package execution.instance.enitty;

import definition.entity.EntityDefinition;
import definition.world.impl.Coordinate;
import execution.instance.property.PropertyInstance;

import java.util.Map;

public interface EntityInstance {
    int getId();
    PropertyInstance getPropertyByName(String name);
    void addPropertyInstance(PropertyInstance propertyInstance);
    EntityDefinition getEntityDef();
    Coordinate getCoordinate();
    void setCoordinate(Coordinate coordinate);
    Map<String, PropertyInstance> getProperties();
}
