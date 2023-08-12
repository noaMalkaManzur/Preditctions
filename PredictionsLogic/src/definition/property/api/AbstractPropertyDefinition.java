package definition.property.api;

import definition.value.generator.api.ValueGenerator;

public abstract class AbstractPropertyDefinition<T> implements PropertyDefinition {

    private final String name;
    private final PropertyType propertyType;
    private final ValueGenerator<T> valueGenerator;
    private final Range range;
    private final Boolean isRandomInit;

    public AbstractPropertyDefinition(String name, PropertyType propertyType, ValueGenerator<T> valueGenerator, Range range,Boolean isRandomInit) {
        this.name = name;
        this.propertyType = propertyType;
        this.valueGenerator = valueGenerator;
        this.range = range;
        this.isRandomInit = isRandomInit;
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

    public Boolean getRandomInit() {
        return isRandomInit;
    }
}
