package definition.property.impl;

import definition.property.api.Range;
import definition.property.api.AbstractPropertyDefinition;
import definition.property.api.PropertyType;
import definition.value.generator.api.ValueGenerator;

public class IntegerPropertyDefinition extends AbstractPropertyDefinition<Integer> {
    public IntegerPropertyDefinition(String name, PropertyType propertyType, ValueGenerator<Integer> valueGenerator, Range range, Boolean isRandomInit) {
        super(name, propertyType, valueGenerator, range, isRandomInit);
    }
}
