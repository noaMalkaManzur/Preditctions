package execution.instance.property;

import definition.property.api.PropertyDefinition;

import java.util.ArrayList;
import java.util.List;

public class PropertyInstanceImpl implements PropertyInstance {

    private final PropertyDefinition propertyDefinition;
    private Object value;
    private int currTickForValueChanged;
    private List<Object> valuesList= new ArrayList<>();
    private boolean isValueChanged = false;
    private int lastChangedTick = 0;

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
        if (!isValueChanged) {
            lastChangedTick = currTickForValueChanged;
            isValueChanged = true;
        }
        valuesList.add(val);
    }

    @Override
    public int getCurrTickForValueChanged() {
        return currTickForValueChanged;
    }

    @Override
    public List<Object> getValuesList() {
        return valuesList;
    }

    @Override
    public double averageTickNumbSinceChangeValue(int SimulationTicks) {
        int totalTicksNoChange = 0;
        int ticksSinceLastChange = 0;

        for (int tick = 0; tick <= SimulationTicks; tick++) {
            if (tick == lastChangedTick) {
                ticksSinceLastChange = 0;
            } else {
                ticksSinceLastChange++;
                totalTicksNoChange += ticksSinceLastChange;
            }
        }
        return (double) totalTicksNoChange / (SimulationTicks + 1);
    }

    @Override
    public double averageValueProperty() {
      try{
          double sum=0;
          for(Object value: valuesList){
            sum = (double)value;
        }
        return sum / valuesList.size();
      }
      catch(Exception e){
          throw new IllegalArgumentException("property is not a number");
      }
    }

}
