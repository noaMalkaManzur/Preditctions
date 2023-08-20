package definition.property.api;

import definition.value.generator.api.ValueGenerator;

public abstract class AbstractPropertyDefinition<T> implements PropertyDefinition {

    private final String name;
    private final PropertyType propertyType;
    private final ValueGenerator<T> valueGenerator;
    private final Range range;
    private final Boolean isRandomInit;
    private int tickPropertyGotValue;

    public AbstractPropertyDefinition(String name, PropertyType propertyType, ValueGenerator<T> valueGenerator, Range range,Boolean isRandomInit, int tickPropertyGotValue ) {
        this.name = name;
        this.propertyType = propertyType;
        this.valueGenerator = valueGenerator;
        this.range = range;
        this.isRandomInit = isRandomInit;
        this.tickPropertyGotValue = tickPropertyGotValue;
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
    @Override
    public Boolean getRandomInit() {
        return isRandomInit;
    }
    @Override
    public int getTickPropertyGotValue(){
        return tickPropertyGotValue;
    }
}
