package Actions;

import Entities.EntityInstance;

public class Calculation extends Action{
    private String funcToActivate;
    private double arg1;
    private double arg2;
    private String resPropName;

    public Calculation(String funcToActivate, double arg1, double arg2, String resPropName) {
        this.funcToActivate = funcToActivate;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.resPropName = resPropName;
    }

    @Override
    public void DoAction(EntityInstance entityInstance)
    {
        try {
            Object MyProp = entityInstance.getPropValues().get(resPropName);
            switch (funcToActivate) {
                case "multiply":
                    MyProp = multyply(arg1, arg2);
                    break;
                case "divide":
                    MyProp = divide(arg1, arg2);
                default:
                    break;
            }
        }catch (Exception ignored)
        {}

    }

    private double multyply(double arg1, double arg2)
    {
        return arg1*arg2;
    }
    private double divide(double arg1,double arg2)
    {
        if(arg2 !=0)
        {
            return arg1/arg2;
        }
        else
        {
            throw new ArithmeticException("Can't divide by 0!");
        }
    }

}
