package definition.entity;

import definition.property.api.PropertyDefinition;

import java.util.Map;

public interface EntityDefinition {
    String getName();
    int getPopulation();
    Map<String,PropertyDefinition> getProps();
    void addPropertyDefinition(PropertyDefinition propertyDefinition);
    void setPopulation(Integer population);

}
