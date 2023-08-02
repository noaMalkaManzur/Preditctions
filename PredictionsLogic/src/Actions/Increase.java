package Actions;

import Entities.Entity;
import Entities.EntityProperty;
import Utilities.Enums;

public class Increase extends Action
{
    private final Object increase_val;
    private final String property;

    public Increase(Entity entity, Object increase_val,String property) {
        super(entity);
        this.increase_val = increase_val;
        this.property = property;
    }

    @Override
    public void DoAction()
    {
        try {
            EntityProperty MyProp = entity.getEntProperties().get(property);

            if (MyProp != null) {
                if (MyProp.getType() == Enums.eTypes.eDecimal) {
                    int MyNewVal = (int) MyProp.getPropVal() + (int) increase_val;
                    if(MyNewVal <= MyProp.getRangeTo())
                        MyProp.setPropVal(MyNewVal);
                    else
                    {
                        throw new RuntimeException("Value was set above limits!");
                    }
                }
                if (MyProp.getType() == Enums.eTypes.eFloat) {
                    Double MyVal = (Double)MyProp.getPropVal();
                    float MyNewVal =  MyVal.floatValue() + ((Number) increase_val).floatValue();
                    if(MyNewVal <= MyProp.getRangeTo())
                        MyProp.setPropVal(MyNewVal);
                    else
                    {
                        throw new RuntimeException("Value was set above limits!");
                    }
                } else {
                    throw new Exception("Invalid property Type");
                }
            } else {
                throw new Exception("No such property");
            }
        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }


    }
}
