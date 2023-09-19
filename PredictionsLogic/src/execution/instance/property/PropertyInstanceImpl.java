package execution.instance.property;

import definition.property.api.PropertyDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyInstanceImpl implements PropertyInstance {

    private final PropertyDefinition propertyDefinition;
    private Object value;
    private int currTickForValueChanged;
    private List<Object> valuesList= new ArrayList<>();
    Map<Integer, List<Object>> tickToValuesMap = new HashMap<>();


    public PropertyInstanceImpl(PropertyDefinition propertyDefinition, Object value) {
        this.propertyDefinition = propertyDefinition;
        this.value = value;
        valuesList.add(value);
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
    public void updateValue(Object val, int currTickForValueChanged) {

        this.value = val;
        this.currTickForValueChanged = currTickForValueChanged;
        valuesList.add(val);
        List<Object> valuesForTick = tickToValuesMap.computeIfAbsent(currTickForValueChanged, k -> new ArrayList<>());
        valuesForTick.add(val);
    }

    @Override
    public int getCurrTickForValueChanged() {
        return currTickForValueChanged;
    }

    @Override
    public List<Object> getValuesList() {
        return valuesList;
    }
}
