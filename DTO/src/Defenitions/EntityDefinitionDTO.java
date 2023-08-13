package Defenitions;

import java.util.Map;

public class EntityDefinitionDTO
{
    private final String name;
    private final int population;
    private final Map<String, PropertyDefinitionDTO> propertyDefinition;

    public EntityDefinitionDTO(String name, int population, Map<String, PropertyDefinitionDTO> propertyDefinition) {
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

    public Map<String, PropertyDefinitionDTO> getPropertyDefinition() {
        return propertyDefinition;
    }
}
