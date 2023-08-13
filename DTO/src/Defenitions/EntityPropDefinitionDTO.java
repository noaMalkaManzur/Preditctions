package Defenitions;

import definition.property.api.PropertyType;
import definition.property.api.Range;

public class EntityPropDefinitionDTO
{
    private final String name;
    private final PropertyType type;
    private final boolean isRandomInit;
    private final Range range;

    public EntityPropDefinitionDTO(String name, PropertyType type, boolean isRandomInit, Range range) {
        this.name = name;
        this.type = type;
        this.isRandomInit = isRandomInit;
        this.range = range;
    }

    public String getName() {
        return name;
    }

    public PropertyType getType() {
        return type;
    }

    public boolean isRandomInit() {
        return isRandomInit;
    }

    public Range getRange() {
        return range;
    }
}
