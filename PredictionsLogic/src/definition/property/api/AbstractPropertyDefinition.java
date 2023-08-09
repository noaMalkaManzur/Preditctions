package definition.property.api;

import definition.value.generator.api.ValueGenerator;

public abstract class AbstractPropertyDefinition<T> implements PropertyDefinition {

    private final String name;
    private final PropertyType propertyType;
    private final ValueGenerator<T> valueGenerator;
    private final Range range;

    public AbstractPropertyDefinition(String name, PropertyType propertyType, ValueGenerator<T> valueGenerator, Range range) {
        this.name = name;
        this.propertyType = propertyType;
        this.valueGenerator = valueGenerator;
        this.range = range;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PropertyType getType() {
        return propertyType;
    }

    @Override
    public T generateValue() {
        return valueGenerator.generateValue();
    }
    @Override
    public Range getRange() {return range;}
}
