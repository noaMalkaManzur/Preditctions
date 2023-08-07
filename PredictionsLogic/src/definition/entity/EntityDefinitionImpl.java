package definition.entity;

import java.util.ArrayList;
import java.util.List;

import definition.property.api.PropertyDefinition;

public class EntityDefinitionImpl implements EntityDefinition {

    private final String name;
    private final int population;
    private final List<PropertyDefinition> properties;

    public EntityDefinitionImpl(String name, int population) {
        this.name = name;
        this.population = population;
        properties = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPopulation() {
        return population;
    }

    @Override
    public List<PropertyDefinition> getProps() {
        return properties;
    }

    @Override
    public void addPropertyDefinition(PropertyDefinition propertyDefinition) {
        properties.add(propertyDefinition);
    }

}
