package definition.entity;

import java.util.List;

import definition.property.api.PropertyDefinition;

public interface EntityDefinition {
    String getName();
    int getPopulation();
    List<PropertyDefinition> getProps();
}
