package Actions;
import Entities.*;
import Utilities.Enums;
import definition.property.api.Range;


public class Increase extends Action
{
    private double increaseVal;
    private String property;

    public Increase(double increaseVal, String property) {
        this.increaseVal = increaseVal;
        this.property = property;
    }

    public double getIncreaseVal() {
        return increaseVal;
    }

    public void setIncreaseVal(double increaseVal) {
        this.increaseVal = increaseVal;
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
                    int MyRes = (int) MyProp + (int) increaseVal;
                    if (MyRange != null && MyRes <= MyPropData.getRange().getRangeTo())
                        MyProp = MyRes;
                    else {
                        throw new RuntimeException("Value is above Range");
                    }
                } else if (MyPropType == Enums.eTypes.eFloat) {
                    double MyRes = (double) MyProp + increaseVal;
                    if (MyRange != null && MyRes <= MyPropData.getRange().getRangeTo())
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
}
