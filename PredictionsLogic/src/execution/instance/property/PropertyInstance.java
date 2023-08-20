package execution.instance.property;

import definition.property.api.PropertyDefinition;

public interface PropertyInstance {
    PropertyDefinition getPropertyDefinition();
    Object getValue();
    void updateValue(Object val);
    void setCurrTickForValueChanged(int currTickForValueChanged);
    int getCurrTickForValueChanged();

}
