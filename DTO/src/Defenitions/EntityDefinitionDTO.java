package Defenitions;

import java.util.Map;

public class EntityDefinitionDTO
{
    private final String name;
    private final int population;
    private final Map<String, EntityPropDefinitionDTO> propertyDefinition;

    public EntityDefinitionDTO(String name, int population, Map<String, EntityPropDefinitionDTO> propertyDefinition) {
        this.name = name;
        this.population = population;
        this.propertyDefinition = propertyDefinition;
    }
    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public Map<String, EntityPropDefinitionDTO> getPropertyDefinition() {
        return propertyDefinition;
    }
}
