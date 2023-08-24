package definition.property.impl;


import definition.property.api.Range;
import definition.property.api.AbstractPropertyDefinition;
import definition.property.api.PropertyType;
import definition.value.generator.api.ValueGenerator;

public class FloatPropertyDefinition extends AbstractPropertyDefinition<Float> {


    public FloatPropertyDefinition(String name, PropertyType propertyType, ValueGenerator<Float> valueGenerator, Range range, Boolean isRandomInit) {
        super(name, propertyType, valueGenerator, range, isRandomInit);
    }
}
