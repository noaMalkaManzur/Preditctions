package definition.entity;

import java.util.List;
import java.util.Map;

import definition.property.api.PropertyDefinition;

public interface EntityDefinition {
    String getName();
    int getPopulation();
    Map<String,PropertyDefinition> getProps();
    void addPropertyDefinition(PropertyDefinition propertyDefinition);

    }
