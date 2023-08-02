package Actions;

import Entities.Entity;
import Entities.EntityProperty;
import Utilities.Enums;

public class Decrease extends Action{
    private final Object decrease_val;
    private final String property;

    public Decrease(Entity entity, Object increase_val,String property) {
        super(entity);
        this.decrease_val = increase_val;
        this.property = property;
    }

    @Override
    public void DoAction()
    {
        try {
            EntityProperty MyProp = entity.getEntProperties().get(property);

            if (MyProp != null) {
                if (MyProp.getType() == Enums.eTypes.eDecimal) {
                    int MyNewVal = (int) MyProp.getPropVal() - (int) decrease_val;
                    if(MyNewVal >= MyProp.getRangeFrom())
                        MyProp.setPropVal(MyNewVal);
                    else
                    {
                        throw new RuntimeException("Value was set under limits!");
                    }
                }
                if (MyProp.getType() == Enums.eTypes.eFloat) {
                    Double MyVal = (Double)MyProp.getPropVal();
                    float MyNewVal =  MyVal.floatValue() - ((Number) decrease_val).floatValue();
                    if(MyNewVal <= MyProp.getRangeTo())
                        MyProp.setPropVal(MyNewVal);
                    else
                    {
                        throw new RuntimeException("Value was set under limits!");
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
