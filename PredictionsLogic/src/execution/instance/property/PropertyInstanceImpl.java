package execution.instance.property;

import definition.property.api.PropertyDefinition;

public class PropertyInstanceImpl implements PropertyInstance {

    private final PropertyDefinition propertyDefinition;
    private Object value;
    private int currTickForValueChanged;

    public PropertyInstanceImpl(PropertyDefinition propertyDefinition, Object value) {
        this.propertyDefinition = propertyDefinition;
        this.value = value;
    }
    @Override
    public PropertyDefinition getPropertyDefinition() {
        return propertyDefinition;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void updateValue(Object val) {
        this.value = val;
    }
    @Override
    public void setCurrTickForValueChanged(int currTickForValueChanged) {
        this.currTickForValueChanged = currTickForValueChanged;
    }
    @Override
    public int getCurrTickForValueChanged() {
        return currTickForValueChanged;
    }
}
