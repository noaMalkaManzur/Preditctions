package Defenitions;

import definition.property.api.PropertyType;
import definition.property.api.Range;

public class EnvPropertyDefinitionDTO {
    private final String name;
    private final PropertyType type;
    private final Range range;

    public EnvPropertyDefinitionDTO(String name, PropertyType type, Range range) {
        this.name = name;
        this.type = type;
        this.range = range;
    }

    public String getName() {
        return name;
    }

    public PropertyType getType() {
        return type;
    }

    public Range getRange() {
        return range;
    }
}
