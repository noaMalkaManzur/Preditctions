package definition.property.impl;

import definition.property.api.AbstractPropertyDefinition;
import definition.property.api.PropertyType;
import definition.property.api.Range;
import definition.value.generator.api.ValueGenerator;

public class StringPropertyDefinition extends AbstractPropertyDefinition<String> {


    public StringPropertyDefinition(String name, PropertyType propertyType, ValueGenerator<String> valueGenerator, Boolean isRandomInit) {
        super(name, propertyType, valueGenerator, null, isRandomInit);
    }
}