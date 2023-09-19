package execution.instance.property;

import definition.property.api.PropertyDefinition;

import java.util.List;

public interface PropertyInstance {
    PropertyDefinition getPropertyDefinition();
    Object getValue();
    void updateValue(Object val, int currTickForValueChanged);
    int getCurrTickForValueChanged();
    List<Object> getValuesList();

    }
