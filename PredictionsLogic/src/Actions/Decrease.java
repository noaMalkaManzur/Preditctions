package Actions;

import Entities.Entity;
import Entities.EntityInstance;
import Entities.EntityProperty;
import Entities.Range;
import Utilities.Enums;

public class Decrease extends Action{
    private  double decrease_val;
    private  String property;

    public Decrease(double decrease_val, String property) {
        this.decrease_val = decrease_val;
        this.property = property;
    }

    @Override
    public void DoAction(EntityInstance entityInstance)
    {
        try {
            Object MyProp = entityInstance.getPropValues().get(property);
            if (MyProp != null) {
                EntityProperty MyPropData = entityInstance.getEntityData().getEntProperties().get(property);
                Enums.eTypes MyPropType = MyPropData.getType();
                Range MyRange = MyPropData.getRange();
                if (MyPropType == Enums.eTypes.eDecimal) {
                    int MyRes = (int) MyProp - (int) decrease_val;
                    if (MyRange != null && MyRes >= MyRange.getRangeFrom())
                        MyProp = MyRes;
                    else {
                        throw new RuntimeException("Value is above Range");
                    }
                } else if (MyPropType == Enums.eTypes.eFloat) {
                    double MyRes = (double) MyProp - decrease_val;
                    if (MyRange != null && MyRes >= MyRange.getRangeFrom())
                        MyProp = MyRes;
                    else {
                        throw new RuntimeException("Value is above Range");
                    }
                } else {
                    throw new RuntimeException("Wrong Type for this action");
                }
            }
        }catch (Exception ignore)
        {
        }
    }

    public String getProperty() {
        return property;
    }

    public double getDecrease_val() {
        return decrease_val;
    }
}
